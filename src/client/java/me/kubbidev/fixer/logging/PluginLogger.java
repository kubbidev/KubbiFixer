package me.kubbidev.fixer.logging;

/**
 * Represents the logger instance being used by Loom on the platform.
 *
 * <p>Messages sent using the logger are sent prefixed with the Loom tag,
 * and on some implementations will be colored depending on the message type.</p>
 */
public interface PluginLogger {

    void info(String s);

    void warn(String s);

    void warn(String s, Throwable t);

    void severe(String s);

    void severe(String s, Throwable t);

}
