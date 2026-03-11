package org.appa.appasUsefulThings;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;


public class Logger {
    private final JavaPlugin plugin;
    private LogLevel logLevel;

    public Logger(JavaPlugin plugin) {
        this(plugin, LogLevel.INFO);
    }

    public Logger(JavaPlugin plugin, LogLevel logLevel) {
        this.plugin = plugin;
        this.logLevel = logLevel;
    }

    public void log(LogLevel level, String message) {
        this.logLevel = level;
        switch (level) {
            case INFO -> plugin.getComponentLogger().info(Component.text(message));
            case WARN -> plugin.getComponentLogger().warn(Component.text(message));
            case ERROR -> plugin.getComponentLogger().error(Component.text(message));
        }
    }

    public void log(String message) {
        switch (this.logLevel) {
            case INFO -> plugin.getComponentLogger().info(Component.text(message));
            case WARN -> plugin.getComponentLogger().warn(Component.text(message));
            case ERROR -> plugin.getComponentLogger().error(Component.text(message));
        }
    }

    public enum LogLevel { INFO, WARN, ERROR }
}
