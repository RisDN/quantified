package hu.ris.quantified.fabric.listeners;

import java.util.Arrays;
import java.util.List;

import hu.ris.quantified.fabric.Quantified;

public class Listeners {

    private static final List<Listener> listeners = Arrays.asList(new EndTickListener(), new ServerStartListener());

    public static void register() {
        for (Listener listener : listeners) {
            listener.register();
            Quantified.LOGGER.info("Listener registered: " + listener.getClass().getSimpleName());
        }
    }

}
