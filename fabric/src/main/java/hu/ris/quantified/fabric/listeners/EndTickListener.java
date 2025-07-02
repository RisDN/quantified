package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.common.config.QuantifiedConfig;
import hu.ris.quantified.fabric.Upload;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class EndTickListener implements Listener {
    private static int tickCount = 0;

    public void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            if (AutoSaveSettings.isAutoSaveEnabled()) {
                tickCount++;

                if (tickCount == QuantifiedConfig.SAVE_INTERVAL) {
                    Upload.uploadStats(server);
                    tickCount = 0;
                }
            } else {
                tickCount = 0;
            }

        });

    }

    public static void resetTickCount() {
        tickCount = 0;
    }

    public static int getTickCount() {
        return tickCount;
    }

}
