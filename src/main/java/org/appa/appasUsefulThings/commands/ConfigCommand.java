package org.appa.appasUsefulThings.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.appa.appasUsefulThings.AppasUsefulThings;
import org.appa.appasUsefulThings.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ConfigCommand implements CommandExecutor, TabCompleter {
    public void register(JavaPlugin plugin) {
        Objects.requireNonNull(plugin.getCommand("config")).setExecutor(this);
    }

    final TextComponent help = Component.text()
            .append(Component.text("/config", NamedTextColor.DARK_GREEN))
            .append(Component.text(" - Brings up this text\n", NamedTextColor.YELLOW))
            .append(Component.text("/config reload", NamedTextColor.GREEN))
            .append(Component.text(" - Reloads the configuration.\n", NamedTextColor.YELLOW))
            .append(Component.text("/config regenerate", NamedTextColor.GREEN))
            .append(Component.text(" - Regenerates the configuration file", NamedTextColor.YELLOW))
            .build();

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length < 1) {
            commandSender.sendMessage(help);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            AppasUsefulThings.getMyLogger().log("Registering config!");
            commandSender.sendMessage(Component.text(
                    "Config for Appa's Useful Things has been reloaded!",  NamedTextColor.GREEN));

            AppasUsefulThings.getPlugin().reloadConfig();
            AppasUsefulThings.config = YamlConfiguration.loadConfiguration(AppasUsefulThings.getConfigFile());
            Config.checkConfig();
            return true;
        }

        if (args[0].equalsIgnoreCase("regenerate")) {
            AppasUsefulThings.getMyLogger().log("Regenerated config!");
            commandSender.sendMessage(Component.text(
                    "Config for Appa's Useful Things has been regenerated!", NamedTextColor.GREEN));

            AppasUsefulThings.getPlugin().saveResource("config.yml", true);
            return true;
        }

        commandSender.sendMessage(help);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String
            @NotNull [] strings
    ) {
        return List.of("reload", "regenerate");
    }
}
