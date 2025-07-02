package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.common.cache.StatisticsCache;
import hu.ris.quantified.common.cache.WorldIconCache;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import hu.ris.quantified.fabric.storage.SaveStatus;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerStartListener implements Listener {

    @Override
    public void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            QuantifiedServerIdentifier.init(server);
            AutoSaveSettings.init(server);
            SaveStatus.init();
            StatisticsCache.clearCache();
            WorldIconCache.clearCache();
            EndTickListener.resetTickCount();
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            SaveStatus.reset();
            StatisticsCache.clearCache();
            WorldIconCache.clearCache();
            EndTickListener.resetTickCount();
        });
    }

}
