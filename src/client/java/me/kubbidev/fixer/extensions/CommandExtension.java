package me.kubbidev.fixer.extensions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import me.kubbidev.fixer.KubbiFixerClient;
import me.kubbidev.fixer.extension.Extension;
import me.kubbidev.fixer.util.EventPriority;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class CommandExtension implements Extension {
    private static final String[] COMMANDS = { "wakes_debug" };

    private final KubbiFixerClient client;

    public CommandExtension(KubbiFixerClient client) {
        this.client = client;
    }

    @Override
    public void load() {
        // register fabric platform listener
        ClientCommandRegistrationCallback.EVENT.register(EventPriority.LOWEST, this::unregisterCommands);
    }

    @Override
    public void unload() {

    }

    private void unregisterCommands(CommandDispatcher<?> dispatcher, CommandRegistryAccess registryAccess) {
        CommandNode<?> rootCommand = dispatcher.getRoot();

        try {
            Field children = CommandNode.class.getDeclaredField("children");
            Field literals = CommandNode.class.getDeclaredField("literals");
            Field arguments = CommandNode.class.getDeclaredField("arguments");

            if (children.trySetAccessible()) {
                //noinspection unchecked
                removeCommands((Map<String, ?>) children.get(rootCommand));
            }

            if (literals.trySetAccessible()) {
                //noinspection unchecked
                removeCommands((Map<String, ?>) literals.get(rootCommand));
            }

            if (arguments.trySetAccessible()) {
                //noinspection unchecked
                removeCommands((Map<String, ?>) arguments.get(rootCommand));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.client.getLogger().warn("Exception unregistering commands", e);
        }
    }

    private void removeCommands(Map<String, ?> registry) {
        // unregister all provided command aliases from the map
        Arrays.stream(COMMANDS).forEach(registry::remove);
    }
}
