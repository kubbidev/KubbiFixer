package me.kubbidev.fixer.util;

import me.kubbidev.fixer.KubbiFixer;
import net.minecraft.util.Identifier;

/**
 * Represents an event's priority in execution.
 * <p>
 * Listeners with lower priority are called first
 * will listeners with higher priority are called last.
 * <p>
 * Listeners are called in following order:
 * {@link #LOWEST} -> {@link #LOW} -> {@link #NORMAL} -> {@link #HIGH} -> {@link #HIGHEST} -> {@link #MONITOR}
 */
public final class EventPriority {
    private EventPriority() {
    }

    /**
     * Event call is of very low importance and should be run first, to allow
     * other plugins to further customise the outcome
     */
    public static final Identifier LOWEST = new Identifier(KubbiFixer.MOD_ID, "lowest");
    /**
     * Event call is of low importance
     */
    public static final Identifier LOW = new Identifier(KubbiFixer.MOD_ID, "low");
    /**
     * Event call is neither important nor unimportant, and may be run
     * normally
     */
    public static final Identifier NORMAL = new Identifier(KubbiFixer.MOD_ID, "normal");
    /**
     * Event call is of high importance
     */
    public static final Identifier HIGH = new Identifier(KubbiFixer.MOD_ID, "high");
    /**
     * Event call is critical and must have the final say in what happens
     * to the event
     */
    public static final Identifier HIGHEST = new Identifier(KubbiFixer.MOD_ID, "highest");
    /**
     * Event is listened to purely for monitoring the outcome of an event.
     * <p>
     * No modifications to the event should be made under this priority
     */
    public static final Identifier MONITOR = new Identifier(KubbiFixer.MOD_ID, "monitor");
}
