package hu.ris.quantified.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

public class StatisticsCache {

    @Getter
    private static final Map<UUID, Map<String, Integer>> cache = new HashMap<>();

    public static Map<String, Integer> getDifferences(UUID player, Map<String, Integer> newData) {
        Map<String, Integer> cachedData = cache.getOrDefault(player, new HashMap<>());
        Map<String, Integer> differences = new HashMap<>();

        for (Map.Entry<String, Integer> entry : newData.entrySet()) {
            String key = entry.getKey();
            Integer newValue = entry.getValue();
            Integer cachedValue = cachedData.get(key);

            if (cachedValue == null || !cachedValue.equals(newValue)) {
                differences.put(key, newValue);
            }
        }

        return differences;

    }

    public static void clearCache() {
        cache.clear();
    }

    public static void updateCache(UUID player, Map<String, Integer> newData) {
        Map<String, Integer> cachedData = cache.getOrDefault(player, new HashMap<>());
        for (Map.Entry<String, Integer> entry : newData.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            cachedData.put(key, value);
        }
        cache.put(player, cachedData);
    }

}
