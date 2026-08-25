package org.appa.appasUsefulThings;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Logger {
    private final JavaPlugin plugin;
    private final LogLevel logLevel;
    private final Component prefix;

    private Logger(Builder builder) {
        this.plugin = builder.plugin;
        this.logLevel = builder.logLevel;

        if (builder.prefix != null) {
            this.prefix = builder.prefix;
            return;
        }

        String loggerPrefix = this.plugin.getPluginMeta().getLoggerPrefix();
        this.prefix = loggerPrefix != null
                ? Component.text(loggerPrefix)
                : Component.empty();
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private JavaPlugin plugin;
        private LogLevel logLevel = LogLevel.INFO;
        private Component prefix;

        public @NotNull Builder setPrefix(@NonNull Component prefix) {
            this.prefix = prefix;
            return this;
        }

        public @NotNull Builder setDefaultLogLevel(@NonNull LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public @NotNull Logger build(@NonNull JavaPlugin plugin) {
            this.plugin = plugin;
            return new Logger(this);
        }
    }


    /**
     * Logs a message using the default log level.
     * @param message The message to send to console.
     */
    public void log(@NonNull String message) {
        log(Component.text(message));
    }

    /**
     * Logs a message using the default log level.
     * @param message The message to send to console.
     */
    public void log(@NonNull Component message) {
        log(this.logLevel, message);
    }

    /**
     * Logs a message using a specific log level.
     * @param level The level to log at.
     * @param message The message to send to console.
     */
    public void log(@NonNull LogLevel level, @NonNull String message) {
        log(level, Component.text(message));
    }

    /**
     * Logs a message to console using a specific log level.
     * @param logLevel The log level to log at.
     * @param message The message to send to console.
     */
    public void log(@NonNull LogLevel logLevel, @NonNull Component message) {
        Component full = Component.empty()
                .append(prefix)
                .append(Component.space())
                .append(message.colorIfAbsent(NamedTextColor.WHITE));

        switch (logLevel) {
            case INFO -> Bukkit.getConsoleSender().sendMessage(full); // TODO: Find a better way then this shit.
            case WARN -> this.plugin.getComponentLogger().warn(message);
            case ERROR -> this.plugin.getComponentLogger().error(message);
        }
    }
    public enum LogLevel { INFO, WARN, ERROR }
}
