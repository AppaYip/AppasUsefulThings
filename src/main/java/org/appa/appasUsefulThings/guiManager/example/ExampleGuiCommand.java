package org.appa.appasUsefulThings.guiManager.example;

import org.appa.appasUsefulThings.AppasUsefulThings;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.appa.appasUsefulThings.AppasUsefulThings.config;

public class ExampleGuiCommand implements CommandExecutor {
    public void register(JavaPlugin plugin) {
        Objects.requireNonNull(plugin.getCommand("ExampleGui")).setExecutor(this);
    }

    GuiManager guiManager = AppasUsefulThings.getGuiManager();

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] args
    ) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can execute this command!");
            return true;
        }

        if (!(config.getBoolean("GuiRoot.ExampleGuiEnabled"))) {
            commandSender.sendMessage("ExampleCooldownCommand GUI is not enabled! To use this command, enable it in the config!");
            return true;
        }

        guiManager.open((Player) commandSender, "ExampleGui");
        return true;
    }
}
