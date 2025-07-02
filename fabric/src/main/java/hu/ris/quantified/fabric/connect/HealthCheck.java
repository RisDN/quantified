package hu.ris.quantified.fabric.connect;

import com.google.gson.JsonObject;

import hu.ris.quantified.common.config.QuantifiedConfig;
import hu.ris.quantified.common.upload.HttpUtils;
import hu.ris.quantified.common.upload.HttpUtils.HttpResponseWithStatus;
import hu.ris.quantified.fabric.Quantified;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class HealthCheck {

    public static void performHealthCheck(ServerPlayerEntity player, String saveKey) {
        String worldId = QuantifiedServerIdentifier.getCurrentId().toString();

        JsonObject data = new JsonObject();
        data.addProperty("key", saveKey);
        data.addProperty("worldId", worldId);

        HttpUtils.postJsonAsyncWithStatus(QuantifiedConfig.HEALTHCHECK_URL, data).thenAccept((response) -> {
            handleHealthCheckResponse(player, response, saveKey);
        });
    }

    private static void handleHealthCheckResponse(ServerPlayerEntity player, HttpResponseWithStatus response, String saveKey) {
        if (response.statusCode == 404) {
            // Save not found or other error - delete the key
            QuantifiedSaveConnection.removeSaveId(QuantifiedServerIdentifier.getCurrentId());
            Quantified.log("Health check failed (404), removed save connection for key: " + saveKey);

            // Send the messageKey from server response
            if (response.body.has("messageKey")) {
                String messageKey = response.body.get("messageKey").getAsString();
                player.sendMessage(Text.translatable(messageKey), false);
            }
        } else if (response.statusCode == 200) {
            // Success - send success message with save name
            if (response.body.has("messageKey")) {
                String messageKey = response.body.get("messageKey").getAsString();

                if (response.body.has("data")) {
                    JsonObject saveData = response.body.get("data").getAsJsonObject();
                    if (saveData.has("name")) {
                        String saveName = saveData.get("name").getAsString();
                        player.sendMessage(Text.translatable(messageKey, saveName), false);

                        // Also send auto-save status
                        boolean autoSaveEnabled = AutoSaveSettings.isAutoSaveEnabled();
                        if (autoSaveEnabled) {
                            player.sendMessage(Text.translatable("quantified.join.auto_save_enabled"), false);
                        } else {
                            player.sendMessage(Text.translatable("quantified.join.auto_save_disabled"), false);
                        }
                        return;
                    }
                }

                // Fallback if no data or name
                player.sendMessage(Text.translatable(messageKey), false);
            }
        } else {
            // Other error codes
            if (response.body.has("messageKey")) {
                String messageKey = response.body.get("messageKey").getAsString();
                player.sendMessage(Text.translatable(messageKey), false);
            } else {
                // Fallback error message
                player.sendMessage(Text.translatable("quantified.health-check.error"), false);
            }
        }
    }
}
