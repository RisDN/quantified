package hu.ris.quantified.common.upload;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import hu.ris.quantified.common.cache.StatisticsCache;
import lombok.Getter;

public class UploadPack {

    @Getter
    private final String key;

    @Getter
    private final JsonObject data;

    public UploadPack(String key, Map<String, Integer> data) {
        this.key = key;
        this.data = new JsonObject();
        Map<String, Integer> differences = StatisticsCache.getDifferences(data);
        StatisticsCache.updateCache(data);

        // Group data by prefix
        Map<String, JsonArray> groupedStats = new HashMap<>();

        for (Map.Entry<String, Integer> entry : differences.entrySet()) {
            String fullKey = entry.getKey();
            int value = entry.getValue();

            // Split the key by semicolon to get prefix and item name
            String[] parts = fullKey.split(";", 2);
            if (parts.length == 2) {
                String prefix = parts[0]; // e.g., "custom", "mined", "crafted"
                String itemName = parts[1]; // e.g., "minecraft:stone"

                // Create array for this prefix if it doesn't exist
                if (!groupedStats.containsKey(prefix)) {
                    groupedStats.put(prefix, new JsonArray());
                }

                // Create an object for this stat entry
                JsonObject statEntry = new JsonObject();
                statEntry.addProperty("item", itemName);
                statEntry.addProperty("value", value);

                // Add to the appropriate group
                groupedStats.get(prefix).add(statEntry);
            }
        }

        // Add all grouped stats to the main data object
        for (Map.Entry<String, JsonArray> group : groupedStats.entrySet()) {
            this.data.add(group.getKey(), group.getValue());
        }

        System.out.println("[quantified] Uploading grouped stats: " + groupedStats.keySet());
    }

    public CompletableFuture<JsonObject> execute() {
        System.out.println("[quantified] Uploading stats with key: " + key);
        return HttpUtils.uploadStats(this);
    }
}
