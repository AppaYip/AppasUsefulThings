package org.appa.appasUsefulThings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings {
    private GuiManager guiManager;

    private boolean enableBuildLogging = false;
    private boolean enableGuiManager = false;

    private final Component prefix = Component.text("[", NamedTextColor.WHITE)
            .append(Component.text("AUT", NamedTextColor.GOLD))
            .append(Component.text("]", NamedTextColor.WHITE));

    private final TextColor enabledColor = TextColor.color(0,182,217);

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

    public GuiManager getGuiManager() {
        return guiManager;
    }


    public AppasUsefulThings build(JavaPlugin plugin) {
        if (enableGuiManager) {
            guiManager = new GuiManager();
            Bukkit.getPluginManager().registerEvents(guiManager, plugin);
        }

        if (!(enableBuildLogging)) {
            return this;
        }

        Logger logger = Logger.builder(plugin)
                .setPrefix(prefix)
                .build();

        logger.log(Component.text("Appa's Useful Things is enabled."));

        if (enableGuiManager) {
            logger.log(Component.text("Gui Manager ").color(enabledColor)
                    .append(Component.text("successfully enabled")).color(NamedTextColor.GREEN)
            );
        }

        return this;
    }
}
