package hu.ris.quantified.common.cache;

import java.util.HashMap;
import java.util.Map;

public class StatisticsCache {

    private static Map<String, Integer> currentStats = new HashMap<>();
    private static Map<String, Integer> lastUploadedStats = new HashMap<>();

    public static Map<String, Integer> processNewFullStatsAndGetChanges(Map<String, Integer> newCompleteStats) {
        if (newCompleteStats == null) {
            throw new IllegalArgumentException("New complete stats map must not be null");
        }

        Map<String, Integer> changesForUpload = new HashMap<>();
        for (Map.Entry<String, Integer> entry : newCompleteStats.entrySet()) {
            String key = entry.getKey();
            Integer newValue = entry.getValue();

            if (key == null || newValue == null) {
                throw new IllegalArgumentException("Stat entries in newCompleteStats must not have null keys or values. Offending key: " + key);
            }

            Integer lastUploadedValue = lastUploadedStats.get(key);
            if (lastUploadedValue == null || !lastUploadedValue.equals(newValue)) {
                changesForUpload.put(key, newValue);
            }
        }

        currentStats.clear();
        currentStats.putAll(newCompleteStats);

        return changesForUpload;
    }

}
