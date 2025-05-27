package hu.ris.listeners;

import hu.ris.storage.QuantifiedServerIdentifier;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerStartListener implements Listener {

    @Override
    public void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            QuantifiedServerIdentifier.init(server);
        });
    }

}
