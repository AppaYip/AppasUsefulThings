package org.appa.appasUsefulThings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Logger {
    private final JavaPlugin plugin;

    private LogLevel logLevel = LogLevel.INFO;
    private Component prefix;

    public Logger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static Logger builder(JavaPlugin plugin) {
        return new Logger(plugin);
    }

    public Logger setPrefix(Component prefix) {
        this.prefix = prefix;
        return this;
    }

    public Logger defaultLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Logger build() {
        return this;
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
    public void log(Component message, TextColor color) {
        log(this.logLevel, message.color(color));
    }

    public void log(String message, TextColor color) {
        log(this.logLevel, Component.text(message).color(color));
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
