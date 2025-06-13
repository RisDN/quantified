package hu.ris.quantified.fabric.connect;

import com.google.gson.JsonObject;

import hu.ris.quantified.common.config.QuantifiedConfig;
import hu.ris.quantified.common.upload.HttpUtils;
import hu.ris.quantified.fabric.Quantified;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TrySaveConnect {

    public static void tryConnect(ServerPlayerEntity player, String key, String worldId) {

        JsonObject data = new JsonObject();
        data.addProperty("key", key);
        data.addProperty("worldId", worldId);

        HttpUtils.postJsonAsync(QuantifiedConfig.CONNECT_URL, data).thenAccept((response) -> {

            String reponseMessageKey = response.get("messageKey").getAsString();

            if (response.get("success").getAsBoolean()) {
                JsonObject saveData = response.get("data").getAsJsonObject();
                String responseSaveKey = saveData.get("key").getAsString();
                String responseSaveName = saveData.get("name").getAsString();

                Quantified.log("Connected save with key: " + responseSaveKey + " and name: " + responseSaveName);

                QuantifiedSaveConnection.setSaveId(responseSaveKey);
                player.sendMessage(Text.translatable(reponseMessageKey, responseSaveName));
            } else {
                player.sendMessage(Text.translatable(reponseMessageKey));
            }

        });
    }

}
