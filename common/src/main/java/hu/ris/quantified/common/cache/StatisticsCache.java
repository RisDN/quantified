package hu.ris.quantified.common.cache;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class StatisticsCache {

    @Getter
    private static final Map<String, Integer> cache = new HashMap<>();

    public static Map<String, Integer> getDifferences(Map<String, Integer> newData) {
        Map<String, Integer> differences = new HashMap<>();
        for (Map.Entry<String, Integer> entry : newData.entrySet()) {
            String key = entry.getKey();
            Integer newValue = entry.getValue();
            Integer oldValue = cache.getOrDefault(key, 0);
            if (!newValue.equals(oldValue)) {
                differences.put(key, newValue - oldValue);
            }
        }
        return differences;
    }

    public static void updateCache(Map<String, Integer> newData) {
        for (Map.Entry<String, Integer> entry : newData.entrySet()) {
            String key = entry.getKey();
            Integer newValue = entry.getValue();
            cache.put(key, newValue);
        }
    }

}
