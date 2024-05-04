package me.kubbidev.fixer;

import me.kubbidev.fixer.extension.ExtensionManager;
import me.kubbidev.fixer.extensions.CommandExtension;
import me.kubbidev.fixer.logging.PluginLogger;
import me.kubbidev.fixer.logging.Slf4jPluginLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

public class KubbiFixerClient implements ClientModInitializer {
	private PluginLogger logger;
	private ExtensionManager extensionManager;

	// lifecycle

	@Override
	public void onInitializeClient() {
		// Register the Server startup/shutdown events now
		ClientLifecycleEvents.CLIENT_STARTED.register(this::onClientStarting);
		ClientLifecycleEvents.CLIENT_STOPPING.register(this::onClientStopping);
	}

	private void onClientStarting(MinecraftClient client) {
		this.logger = new Slf4jPluginLogger(KubbiFixer.LOGGER);

		// load all registered modules
		this.extensionManager = new ExtensionManager(this);
		this.extensionManager.loadExtension(CommandExtension.class);
	}

	private void onClientStopping(MinecraftClient client) {
		// unload extensions
		this.extensionManager.close();
	}

	public PluginLogger getLogger() {
		return this.logger;
	}

	public ExtensionManager getExtensionManager() {
		return this.extensionManager;
	}
}