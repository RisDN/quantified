package hu.ris.storage;

import java.util.UUID;

import hu.ris.Quantified;
import lombok.Getter;
import net.minecraft.command.DataCommandStorage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class QuantifiedServerIdentifier {

    private static final Identifier ID = Identifier.of("quantified", "server_identifier");

    @Getter
    private static UUID currentId;

    public static void init(MinecraftServer server) {
        DataCommandStorage storage = server.getDataCommandStorage();
        NbtCompound nbt = storage.get(ID);

        if (nbt == null || !nbt.contains("serverId")) {
            Quantified.log("No server id found, generating a new one.");
            nbt = new NbtCompound();
            UUID id = UUID.randomUUID();
            nbt.putString("serverId", id.toString());
            currentId = id;
            Quantified.log("Generated server id: " + currentId);
        } else {
            currentId = UUID.fromString(nbt.getString("serverId").get());
        }
        Quantified.log("Found server id: " + currentId);
        storage.set(ID, nbt);

    }

}
