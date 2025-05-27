package hu.ris;

import com.google.gson.JsonObject;

import hu.ris.config.QuantifiedConfig;
import hu.ris.storage.QuantifiedDataPack;
import hu.ris.utils.HttpUtils;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class UploadTask {

    @Getter
    private final ServerPlayerEntity player;

    @Getter
    private final MinecraftServer server;

    public UploadTask(QuantifiedDataPack data) {
        this.player = data.getPlayer();
        this.server = data.getServer();

        Quantified.log("UploadTask created for player: " + player.getName().getString());
        this.execute();
    }

    public void execute() {
        JsonObject data = new JsonObject();

        data.addProperty("player", player.getName().getString());
        data.addProperty("score", 42);
        Quantified.log("Data to be sent: " + data);
        HttpUtils.postJsonAsync(QuantifiedConfig.UPLOAD_URL, data).thenAccept(jsonResponse -> {
            Quantified.log("Received response: " + jsonResponse);
            server.execute(() -> {
                if (jsonResponse != null && jsonResponse.has("message")) {
                    String reply = jsonResponse.get("message").getAsString();
                    player.sendMessage(Text.literal("Szerver válasz: " + reply), false);
                } else {
                    player.sendMessage(Text.literal("Sikertelen kérés vagy érvénytelen válasz"), false);
                }

            });
        });

    }

}
