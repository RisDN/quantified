package hu.ris.quantified.common.upload;

import com.google.gson.JsonObject;

import hu.ris.quantified.common.config.QuantifiedConfig;

import java.util.concurrent.CompletableFuture;
import lombok.Getter;

public class UploadTask {

    @Getter
    private final String key;

    @Getter
    private final JsonObject data;

    public UploadTask(String key, JsonObject data) {
        this.key = key;
        this.data = data;
    }

    /**
     * Executes the upload task asynchronously. The actual HTTP call is made here.
     * 
     * @return A CompletableFuture containing the JsonObject response from the
     *         server.
     */
    public CompletableFuture<JsonObject> execute() {
        return HttpUtils.postJsonAsync(QuantifiedConfig.UPLOAD_URL, this.data);
    }

}
