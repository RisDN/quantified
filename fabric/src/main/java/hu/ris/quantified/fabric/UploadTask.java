package hu.ris.quantified.fabric;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Task to upload statistics for all online players
 */
public class UploadTask {

    private final MinecraftServer server;

    public UploadTask(MinecraftServer server) {
        this.server = server;
        execute();
    }

    private void execute() {
        Quantified.log("UploadTask started - uploading stats for all online players");

        // Upload stats for all online players
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            Quantified.log("UploadTask: Uploading stats for player " + player.getName().getString());
            Upload.uploadStats(player, server);
        }

        Quantified.log("UploadTask completed");
    }
}
