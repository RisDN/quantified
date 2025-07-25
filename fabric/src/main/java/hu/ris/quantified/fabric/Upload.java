package hu.ris.quantified.fabric;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import hu.ris.quantified.common.cache.StatisticsCache;
import hu.ris.quantified.common.upload.WorldIconUtils;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import hu.ris.quantified.fabric.storage.SaveStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class Upload {

    public static void uploadStats(MinecraftServer server) {
        if (server == null || server.getPlayerManager() == null) {
            return;
        }

        if (server.isStopping() || server.isStopped()) {
            return;
        }

        if (server.getPlayerManager().getPlayerList().isEmpty()) {
            return;
        }

        String base64Icon = server.getIconFile().map(WorldIconUtils::toBase64).orElse("");

        if (server.isDedicated()) {
            base64Icon = WorldIconUtils.toBase64(server.getRunDirectory().resolve("server-icon.png"));
        }

        UUID serverId = QuantifiedServerIdentifier.getCurrentId();
        String saveKey = QuantifiedSaveConnection.getSaveIdByServerUuid(serverId);

        if (serverId == null || saveKey == null) {
            return;
        }

        UploadPack uploadPack = new UploadPack(saveKey, serverId.toString(), base64Icon);
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        final Map<ServerPlayerEntity, Map<String, Integer>> playerStats = new HashMap<>();
        uploadPack.setDays(Integer.parseInt(server.getOverworld().getTimeOfDay() / 24000L + ""));
        CompletableFuture<?>[] statsFutures = players.stream().map(player -> StatsCollector.getStats(player, server).thenAccept((stats) -> {
            if (stats == null || stats.isEmpty()) {
                return;
            }

            Map<String, Integer> statsAfterCache = StatisticsCache.getDifferences(player.getUuid(), stats);
            synchronized (playerStats) {
                playerStats.put(player, statsAfterCache);
            }
        })).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(statsFutures).thenRunAsync(() -> {
            uploadPack.setPlayerStats(playerStats);

            uploadPack.execute().thenAccept((response) -> {

                boolean success = response.has("success") && response.get("success").getAsBoolean();

                if (success) {
                    Quantified.log("Stats synced successfully!");

                    SaveStatus.setLastSuccessTime(System.currentTimeMillis());

                    for (ServerPlayerEntity player : players) {
                        StatisticsCache.updateCache(player.getUuid(), playerStats.get(player));
                    }

                } else {
                    String errorMessage = response.has("error") ? response.get("error").getAsString() : "Unknown error";
                    Quantified.log("Stats sync failed: " + errorMessage);
                }

            });
        });
    }

}
