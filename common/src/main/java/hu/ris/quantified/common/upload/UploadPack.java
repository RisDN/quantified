package hu.ris.quantified.common.upload;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
        StatisticsCache.updateCache(data); // Add this line
        for (Map.Entry<String, Integer> entry : differences.entrySet()) {
            this.data.addProperty(entry.getKey(), entry.getValue());
        }
        System.out.println("[quantified] Uploading stats: " + differences);
    }

    public CompletableFuture<JsonObject> execute() {
        System.out.println("[quantified] Uploading stats with key: " + key);
        return HttpUtils.uploadStats(this);
    }
}
