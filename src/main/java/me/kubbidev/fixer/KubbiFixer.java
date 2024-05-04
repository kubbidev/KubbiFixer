package me.kubbidev.fixer;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KubbiFixer implements ModInitializer {
	// easy access to our current mod identifier
	public static final String MOD_ID = "kubbifixer";

	// prefix used for logging when writing to the console
	public static final String MOD_PREFIX = "[KubbiFixer] ";

	// this logger is used to write text to the console and the log file.
	static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}
}