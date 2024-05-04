package me.kubbidev.fixer.logging;

import org.slf4j.Logger;

import static me.kubbidev.fixer.KubbiFixer.MOD_PREFIX;

public class Slf4jPluginLogger implements PluginLogger {
    private final Logger logger;

    public Slf4jPluginLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s) {
        this.logger.info(MOD_PREFIX + s);
    }

    @Override
    public void warn(String s) {
        this.logger.warn(MOD_PREFIX + s);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.warn(MOD_PREFIX + s, t);
    }

    @Override
    public void severe(String s) {
        this.logger.error(MOD_PREFIX + s);
    }

    @Override
    public void severe(String s, Throwable t) {
        this.logger.error(MOD_PREFIX + s, t);
    }
}