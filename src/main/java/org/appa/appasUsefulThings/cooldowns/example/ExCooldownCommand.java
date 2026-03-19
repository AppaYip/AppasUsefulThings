package org.appa.appasUsefulThings.cooldowns.example;

import org.appa.appasUsefulThings.AppasUsefulThings;
import org.appa.appasUsefulThings.cooldowns.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.appa.appasUsefulThings.AppasUsefulThings.config;


public class ExCooldownCommand implements CommandExecutor {
    public static void register(JavaPlugin plugin) {
        ExCooldownCommand cmd = new ExCooldownCommand();
        Objects.requireNonNull(plugin.getCommand("CooldownExample")).setExecutor(cmd);
    }

    Random random = new Random();
    String[] quotes = {"", ""};

    CooldownManager cooldownManager = AppasUsefulThings.getCooldownManager();

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        if (config.getBoolean("CooldownRoot.ExampleCommandEnabled")) {
            commandSender.sendMessage(
                "This command has been disabled in config" +
                "To use it, enable it in the config."
            );
            return true;
        }

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can execute this command!");
            return true;
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(cooldownManager.getRemainingMillis(player));

        if (!(cooldownManager.isOver(player))) {
            player.sendMessage("Please wait " +
                    seconds
                    + " before executing this command again"
            );
            return true;
        }

        int randomIndex = random.nextInt(quotes.length);
        player.sendMessage("Your quote is " + quotes[randomIndex]);
        cooldownManager.setCooldown(player, TimeUnit.SECONDS.toMillis(5));
        return true;
    }
}
