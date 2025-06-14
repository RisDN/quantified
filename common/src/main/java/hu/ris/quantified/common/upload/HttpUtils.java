package hu.ris.quantified.common.upload;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

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

}
