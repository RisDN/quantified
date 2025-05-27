package hu.ris.quantified.fabric.listeners;

import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerStartListener implements Listener {

    @Override
    public void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            QuantifiedServerIdentifier.init(server);
        });
    }

}
