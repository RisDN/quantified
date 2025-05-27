package hu.ris.quantified.fabric.storage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import hu.ris.quantified.fabric.storage.statistics.CustomStatisticsHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class QuantifiedDataPack extends CustomStatisticsHelper {

    public QuantifiedDataPack(ServerPlayerEntity player, ServerWorld world, MinecraftServer server) {
        super(player, world, server);

        this.generateJson();
    }

    public void generateJson() {
        // put in player statistics
        // put in world statistics and info
        // write it into a file

        CompletableFuture.runAsync(() -> {
            // read the stats folder by the player's uuid.json

            double days = this.getDays();
            int deaths = this.getDeaths();
            double timeSlept = this.getSleepInBed();
            Map<String, Integer> craftedItems = this.getCraftedItems();
            JsonObject data = new JsonObject();

        });

    }

}
