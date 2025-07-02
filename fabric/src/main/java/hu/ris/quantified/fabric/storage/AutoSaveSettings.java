package hu.ris.quantified.fabric.storage;

import hu.ris.quantified.fabric.Quantified;
import net.minecraft.command.DataCommandStorage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class AutoSaveSettings {

    private static final Identifier ID = Identifier.of("quantified", "auto_save_settings");

    private static boolean autoSaveEnabled = true; // Default enabled

    public static void init(MinecraftServer server) {
        DataCommandStorage storage = server.getDataCommandStorage();
        NbtCompound nbt = storage.get(ID);

        if (nbt == null || !nbt.contains("autoSaveEnabled")) {
            Quantified.log("No auto-save settings found, defaulting to enabled.");
            nbt = new NbtCompound();
            nbt.putBoolean("autoSaveEnabled", true);
            autoSaveEnabled = true;
            Quantified.log("Auto-save enabled by default: " + autoSaveEnabled);
        } else {
            autoSaveEnabled = nbt.getBoolean("autoSaveEnabled").get();
            Quantified.log("Found auto-save setting: " + autoSaveEnabled);
        }
        storage.set(ID, nbt);
    }

    public static void setAutoSaveEnabled(MinecraftServer server, boolean enabled) {
        autoSaveEnabled = enabled;

        DataCommandStorage storage = server.getDataCommandStorage();
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean("autoSaveEnabled", enabled);
        storage.set(ID, nbt);

        Quantified.log("Auto-save setting changed to: " + enabled);
    }

    public static boolean isAutoSaveEnabled() {
        return autoSaveEnabled;
    }
}
