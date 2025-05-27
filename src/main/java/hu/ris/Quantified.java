package hu.ris;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.ris.commands.Commands;
import hu.ris.listeners.Listeners;
import hu.ris.storage.QuantifiedSaveConnection;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

public class Quantified implements ModInitializer {
	public static final String MOD_ID = "quantified";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static @Getter Quantified instance;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		instance = this;
		log("Quantified is starting up!");

		log("Registering listeners...");
		Listeners.register();
		log("Listeners registered!");

		log("Loading commands...");
		Commands.register();
		log("Commands loaded!");

		log("Loading save file...");
		QuantifiedSaveConnection.init();
		log("Save file loaded!");

		log("Quantified is ready!");

	}

	public static void log(String message) {
		LOGGER.info("[" + MOD_ID + "] " + message);
	}

	public static void log(int number) {
		LOGGER.info("[" + MOD_ID + "] " + number);
	}
}