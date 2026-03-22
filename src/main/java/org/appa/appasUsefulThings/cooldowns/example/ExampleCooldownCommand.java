package org.appa.appasUsefulThings.cooldowns.example;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.appa.appasUsefulThings.cooldowns.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.appa.appasUsefulThings.AppasUsefulThings.config;


public class ExampleCooldownCommand implements CommandExecutor {
    public void register(JavaPlugin plugin) {
        Objects.requireNonNull(plugin.getCommand("exampleCooldown")).setExecutor(this);
    }

    Random random = new Random();
    final static String[] quotes = {
            "YOU PARASITE! - Caine", "I'm right behind you aren't I - Kinger",
            "It's not a bug, it's a feature - Shiki",
            "Hexagons are the bestagons - CGP Grey",
            "DIPPER! What is the one thing I asked you not to do tonight? - Mabel", "Raise the dead... - Dipper", "And what did you do? - Mabel",
            "Thaumcraft tomorrow. - The entire CoFH discord",
            "I didn't write that - Garnet"
    };

    CooldownManager cooldownManager = new CooldownManager();

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


        final TextComponent cooldownMessage = Component.text()
                .content("Please wait ")
                .color(NamedTextColor.RED)
                .append(Component.text(cooldownManager.getRemainingSeconds(player)))
                .append(Component.text(" more second(s) before executing this command again"))
                .build();
        if (!(cooldownManager.isOver(player))) {
            player.sendMessage(cooldownMessage);
            return true;
        }

        int randomIndex = random.nextInt(quotes.length);
        final TextComponent quoteMessage = Component.text()
                .content("Your quote is: ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(quotes[randomIndex], NamedTextColor.YELLOW))
                .build();
        player.sendMessage(quoteMessage);

        cooldownManager.setCooldown(player, TimeUnit.SECONDS.toMillis(5));
        return true;
    }
}
