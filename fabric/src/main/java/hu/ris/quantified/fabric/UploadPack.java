package hu.ris.quantified.fabric;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import hu.ris.quantified.common.cache.WorldIconCache;
import hu.ris.quantified.common.config.QuantifiedConfig;
import hu.ris.quantified.common.upload.HttpUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.network.ServerPlayerEntity;

public class UploadPack {

    @Getter
    @Setter
    private Map<ServerPlayerEntity, Map<String, Integer>> playerStats = new HashMap<>();

    @Getter
    private final JsonObject data;

    public UploadPack(String key, String worldId, String base64Icon) {
        this.data = new JsonObject();

        this.data.addProperty("key", key);
        this.data.addProperty("worldId", worldId);

        if (WorldIconCache.isChanged(base64Icon)) {
            WorldIconCache.setWorldIconBase64(base64Icon);
            data.addProperty("icon", base64Icon);
        }

    }

    public JsonObject mapPlayerStats() {
        Quantified.log("Mapping player stats... Count: " + this.playerStats.size());
        JsonObject playerStatsObject = new JsonObject();

        for (Entry<ServerPlayerEntity, Map<String, Integer>> entry : this.playerStats.entrySet()) {
            ServerPlayerEntity player = entry.getKey();
            Map<String, Integer> stats = entry.getValue();

            JsonObject statsData = new JsonObject();

            for (Entry<String, Integer> stat : stats.entrySet()) {
                String group = stat.getKey().split(";")[0];
                String statName = stat.getKey().split(";")[1];
                Integer value = stat.getValue();

                if (!statsData.has(group)) {
                    statsData.add(group, new JsonObject());
                }

                statsData.getAsJsonObject(group).addProperty(statName, value);

            }

            JsonObject playerData = new JsonObject();
            playerData.addProperty("name", player.getName().getString());
            playerData.add("stats", statsData);
            playerStatsObject.add(player.getUuid().toString(), playerData);
        }

        return playerStatsObject;
    }

    public CompletableFuture<JsonObject> execute() {

        data.add("players", mapPlayerStats());

        JsonObject requestData = new JsonObject();
        requestData.add("data", data);

        return HttpUtils.postJsonAsync(QuantifiedConfig.UPLOAD_URL, requestData).thenApply(response -> {
            if (response == null) {
                throw new RuntimeException("Failed to upload stats, no response received.");
            }
            return response;
        });

    }
}
