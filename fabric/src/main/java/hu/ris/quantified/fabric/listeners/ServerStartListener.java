package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.common.cache.StatisticsCache;
import hu.ris.quantified.common.cache.WorldIconCache;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerStartListener implements Listener {

    @Override
    public void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            QuantifiedServerIdentifier.init(server);
            AutoSaveSettings.init(server);
            StatisticsCache.clearCache();
            WorldIconCache.clearCache();
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            StatisticsCache.clearCache();
            WorldIconCache.clearCache();
        });
    }

}
