package org.appa.appasUsefulThings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings {
    private boolean enableBuildLogging = false;
    private boolean enableGuiManager = false;

    private Component prefix = Component.text("[", NamedTextColor.WHITE)
            .append(Component.text("AUT", NamedTextColor.GOLD))
            .append(Component.text("]", NamedTextColor.WHITE));

    private TextColor enabledColor = TextColor.color(0,182,217);

    public static AppasUsefulThings builder() {
        return new AppasUsefulThings();
    }

    public AppasUsefulThings enableBuildLogging() {
        this.enableBuildLogging = true;
        return this;
    }

    public AppasUsefulThings enableGuiManager() {
        this.enableGuiManager = true;
        return this;
    }

    public void build(JavaPlugin plugin) {
        if (enableGuiManager) {
            Bukkit.getPluginManager().registerEvents(new GuiManager(), plugin);
        }


        if (!(enableBuildLogging)) { return; }

        Logger logger = Logger.builder(plugin)
                .setPrefix(prefix)
                .build();

        logger.log(Component.text("Appa's Useful Things is enabled."));

        if (enableGuiManager) {
            logger.log(Component.text("Gui Manager ").color(enabledColor)
                    .append(Component.text(" successfully enabled")).color(NamedTextColor.GREEN)
            );
        }

    }
}

