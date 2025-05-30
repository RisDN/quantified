package hu.ris.quantified.fabric;

import hu.ris.quantified.common.upload.UploadPack;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Upload {

    public static void uploadStats(ServerPlayerEntity player, MinecraftServer server) {
        Quantified.log("Initiating stats upload for player: " + player.getName().getString());
        StatsCollector.getStats(player, server).thenAccept((stats) -> {
            Quantified.log("Stats collected for " + player.getName().getString() + ", preparing upload pack. Number of stats entries: " + (stats != null ? stats.size() : "null"));
            if (stats == null) {
                Quantified.log("[ERROR] Stats collection resulted in null for player: " + player.getName().getString());
                player.sendMessage(Text.translatable("quantified.upload.failure.collection.null"), false);
                return;
            }
            UploadPack pack = new UploadPack(QuantifiedSaveConnection.getSaveIdByServerUuid(QuantifiedServerIdentifier.getCurrentId()), stats);

            Quantified.log("Executing upload pack for: " + player.getName().getString());
            pack.execute().thenAccept((response) -> {
                System.out.println("[quantified] Upload response: " + response); // Keep this for direct console feedback
                if (response == null) {
                    Quantified.log("[ERROR] Failed to upload stats for " + player.getName().getString() + ", no response received from pack.execute().");
                    player.sendMessage(Text.translatable("quantified.upload.failure.noresponse"), false);
                    return;
                }

                Quantified.log("Upload successful for " + player.getName().getString() + ". Response: " + response);
                player.sendMessage(Text.translatable("quantified.upload.success"), false);

            }).exceptionally((ex) -> {
                Quantified.log("[ERROR] Exception during pack.execute() for " + player.getName().getString() + ": " + ex.toString());
                ex.printStackTrace(); // Log the full stack trace for debugging
                player.sendMessage(Text.translatable("quantified.upload.failure.exception"), false);
                return null; // Required for exceptionally if it's not rethrowing
            });
        }).exceptionally((ex) -> {
            Quantified.log("[ERROR] Exception during StatsCollector.getStats() for " + player.getName().getString() + ": " + ex.toString());
            ex.printStackTrace(); // Log the full stack trace
            player.sendMessage(Text.translatable("quantified.upload.failure.collection"), false);
            return null; // Required for exceptionally
        });
    }

}
