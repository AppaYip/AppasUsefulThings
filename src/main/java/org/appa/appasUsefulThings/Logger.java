package org.appa.appasUsefulThings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Logger {
    private final JavaPlugin plugin;
    private final LogLevel logLevel;
    private Component prefix;

    private Logger(Builder builder) {
        this.plugin = builder.plugin;
        this.logLevel = builder.logLevel;
        this.prefix = builder.prefix;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private JavaPlugin plugin;
        private LogLevel logLevel = LogLevel.INFO;
        private Component prefix;

        public Builder setPrefix(Component prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder defaultLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Logger build(JavaPlugin plugin) {
            this.plugin = plugin;
            return new Logger(this);
        }
    }


    // Strings
    public void log(String message) {
        log(this.logLevel, Component.text(message));
    }

    public void log(LogLevel logLevel, String message) {
        log(logLevel, Component.text(message));
    }

    // Components
    public void log(Component message) {
        log(this.logLevel, message);
    }

    // Colors
    public void log(String message, TextColor color) {
        log(this.logLevel, Component.text(message).color(color));
    }

    public void log(LogLevel logLevel, String message, TextColor color) {
        log(logLevel, Component.text(message).color(color));
    }

    // Actual logging method
    public void log(LogLevel logLevel, Component message) {
        if (this.prefix == null) {
            this.prefix = Component.text(this.plugin.getPluginMeta().getLoggerPrefix());
        }

        Component full = this.prefix.append(Component.text(" ").append(message));

        switch (logLevel) {
            case INFO -> Bukkit.getConsoleSender().sendMessage(full);
            case WARN -> this.plugin.getComponentLogger().warn(message);
            case ERROR -> this.plugin.getComponentLogger().error(message);
        }
    }
    public enum LogLevel { INFO, WARN, ERROR }
}
