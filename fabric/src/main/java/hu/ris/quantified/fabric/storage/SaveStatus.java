package hu.ris.quantified.fabric.storage;

import hu.ris.quantified.fabric.Quantified;
import lombok.Getter;

public class SaveStatus {

    @Getter
    private static String saveName = null;

    @Getter
    private static long lastSuccessTime = 0;

    public static void init() {
        // Reset all values on init
        saveName = null;
        lastSuccessTime = 0;
        Quantified.log("Save status initialized - Name: " + saveName + ", Last success: " + lastSuccessTime);
    }

    public static void setSaveName(String name) {
        saveName = name;
        Quantified.log("Save name updated to: " + name);
    }

    public static void setLastSuccessTime(long timestamp) {
        lastSuccessTime = timestamp;
        Quantified.log("Last success time updated to: " + timestamp);
    }

    public static void reset() {
        saveName = null;
        lastSuccessTime = 0;
        Quantified.log("Save status reset");
    }
}
