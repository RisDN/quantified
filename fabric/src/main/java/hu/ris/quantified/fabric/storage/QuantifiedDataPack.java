package hu.ris.quantified.fabric.storage;

import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class QuantifiedDataPack {

    @Getter
    private final ServerPlayerEntity player;

    @Getter
    private final ServerWorld world;

    @Getter
    private final MinecraftServer server;

    @Getter
    private final JsonObject data;

    public QuantifiedDataPack(ServerPlayerEntity player, ServerWorld world, MinecraftServer server) {
        this.generateJson();
        this.player = player;
        this.world = world;
        this.server = server;
        this.data = new JsonObject();
    }

    public void generateJson() {
        // put in player statistics
        // put in world statistics and info
        // write it into a file

        CompletableFuture.runAsync(() -> {

        });

    }

}
