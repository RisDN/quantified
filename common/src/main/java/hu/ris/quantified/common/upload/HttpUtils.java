package hu.ris.quantified.common.upload;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import hu.ris.quantified.common.cache.WorldIconCache;
import hu.ris.quantified.common.config.QuantifiedConfig;

public class HttpUtils {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static CompletableFuture<JsonObject> postJsonAsync(String url, JsonObject jsonData) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).version(Version.HTTP_1_1).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(gson.toJson(jsonData))).build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            try {
                System.out.println("[quantified] HTTP response status: " + response.body());
                return gson.fromJson(response.body(), JsonObject.class);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("Error while sending HTTP request: " + response.body(), e);
            }
        }).exceptionally(e -> {
            System.err.println("Error occurred while sending HTTP request: " + e.getMessage());
            e.printStackTrace();
            return null;
        });
    }

    public static CompletableFuture<JsonObject> uploadStats(UploadPack pack) {
        JsonObject data = new JsonObject();
        data.addProperty("key", pack.getKey());
        data.addProperty("playerUuid", pack.getPlayerUniqueId());
        data.addProperty("playerName", pack.getPlayerName());
        data.addProperty("worldId", pack.getWorldId());

        JsonObject stats = new JsonObject();
        for (String key : pack.getData().keySet()) {
            stats.add(key, pack.getData().get(key));
        }

        if (WorldIconCache.isChanged(pack.getBase64Icon())) {
            WorldIconCache.setWorldIconBase64(pack.getBase64Icon());
            data.addProperty("icon", pack.getBase64Icon());
        }

        JsonObject requestData = new JsonObject();
        data.add("stats", stats);
        requestData.add("data", data);

        System.out.println("Uploading data: " + requestData.toString());

        return postJsonAsync(QuantifiedConfig.UPLOAD_URL, requestData).thenApply(response -> {
            if (response == null) {
                throw new RuntimeException("Failed to upload stats, no response received.");
            }
            return response;
        });
    }

}
