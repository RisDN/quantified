package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.common.config.QuantifiedConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class EndTickListener implements Listener {
    private static int tickCount = 0;

    public void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCount++;
            if (tickCount == QuantifiedConfig.SAVE_INTERVAL) {
                // new UploadTask();
                tickCount = 0;
            }

        });

    }

}
