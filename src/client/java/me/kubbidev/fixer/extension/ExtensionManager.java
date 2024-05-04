package me.kubbidev.fixer.extension;

import me.kubbidev.fixer.KubbiFixerClient;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtensionManager implements AutoCloseable {
    private final KubbiFixerClient client;
    private final Set<LoadedExtension> extensions = new HashSet<>();

    public ExtensionManager(KubbiFixerClient client) {
        this.client = client;
    }

    @Override
    public void close() {
        for (LoadedExtension extension : this.extensions) {
            try {
                extension.instance.unload();
            } catch (Exception e) {
                this.client.getLogger().warn("Exception unloading extension", e);
            }
        }
        this.extensions.clear();
    }

    public void loadExtension(Extension extension) {
        if (this.extensions.stream().anyMatch(e -> e.instance.equals(extension))) {
            return;
        }
        this.client.getLogger().info("Loading extension: " + extension.getClass().getName());
        this.extensions.add(new LoadedExtension(extension, extension.getClass()));
        extension.load();
    }

    public @NotNull Extension loadExtension(Class<? extends Extension> extensionClass) {
        if (this.extensions.stream().anyMatch(e -> extensionClass.equals(e.type))) {
            throw new IllegalStateException("Extension at path " + extensionClass.getName() + " already loaded.");
        }

        this.client.getLogger().info("Loading extension: " + extensionClass.getName());

        Extension extension = null;

        try {
            Constructor<? extends Extension> constructor = extensionClass.getConstructor(KubbiFixerClient.class);
            extension = constructor.newInstance(this.client);
        } catch (NoSuchMethodException e) {
            // ignore
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if (extension == null) {
            try {
                Constructor<? extends Extension> constructor = extensionClass.getConstructor();
                extension = constructor.newInstance();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Unable to find valid constructor in " + extensionClass.getName());
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        this.extensions.add(new LoadedExtension(extension, extensionClass));
        extension.load();
        return extension;
    }

    public @NotNull Collection<Extension> getLoadedExtensions() {
        return this.extensions.stream().map(e -> e.instance).collect(Collectors.toSet());
    }

    private static final class LoadedExtension {
        private final Extension instance;
        private final Class<?> type;

        private LoadedExtension(Extension instance, Class<?> type) {
            this.instance = instance;
            this.type = type;
        }
    }
}
