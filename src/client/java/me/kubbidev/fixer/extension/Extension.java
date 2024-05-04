package me.kubbidev.fixer.extension;

/**
 * Represents a simple extension "plugin" for KubbiFixer.
 *
 * <p>Yes, that's right. A plugin for a plugin.</p>
 *
 * <p>Extensions should either declare a no-arg constructor, or a constructor
 * that accepts a single {@link me.kubbidev.fixer.KubbiFixerClient} parameter as it's only argument.</p>
 */
public interface Extension {

    /**
     * Loads the extension.
     */
    void load();

    /**
     * Unloads the extension.
     */
    void unload();

}