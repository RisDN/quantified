package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.fabric.connect.HealthCheck;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerJoinListener implements Listener {

    @Override
    public void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            boolean isDedicated = server.isDedicated();
            boolean hasPermission = !isDedicated || player.hasPermissionLevel(4);

            if (hasPermission) {

                String saveKey = QuantifiedSaveConnection.getSaveIdByServerUuid(QuantifiedServerIdentifier.getCurrentId());

                if (saveKey == null) {
                    player.sendMessage(Text.translatable("quantified.join.no_save_key"), false);
                } else {
                    // Perform health check
                    HealthCheck.performHealthCheck(player, saveKey);
                }
            }
        });
    }
}
