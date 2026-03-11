package org.appa.appasUsefulThings.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.appa.appasUsefulThings.AppasUsefulThings;
import org.appa.appasUsefulThings.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class ConfigCommand implements CommandExecutor {
    public void register(JavaPlugin plugin) {
        ConfigCommand cmd = new ConfigCommand();
        Objects.requireNonNull(plugin.getCommand("config")).setExecutor(cmd);
    }


    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length != 1) {
            commandSender.sendMessage(
                Component.text(
                    "Usage: /config reload",
                    TextColor.color(255, 0, 0)
                )
            );

            return true;
        }

        AppasUsefulThings.getMyLogger().log("Registering config!");
        commandSender.sendMessage("Config for Appa's Useful Things has been reloaded!");

        AppasUsefulThings.getPlugin().reloadConfig();
        AppasUsefulThings.config = YamlConfiguration.loadConfiguration(AppasUsefulThings.getConfigFile());
        Config.checkConfig();
        return true;
    }
}
