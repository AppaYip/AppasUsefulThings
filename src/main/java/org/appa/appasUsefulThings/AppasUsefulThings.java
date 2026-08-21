package org.appa.appasUsefulThings;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class AppasUsefulThings {
    @Getter private GuiManager guiManager;

    private boolean enableBuildLogging = false;
    private boolean enableGuiManager = false;

    private final Component prefix = Component.text("[", NamedTextColor.WHITE)
            .append(Component.text("AUT", NamedTextColor.GOLD))
            .append(Component.text("]", NamedTextColor.WHITE));

    /**
     * Gets a new instance. This is the main entry point.
     * @return Instance of {@link AppasUsefulThings}
     */
    public static AppasUsefulThings builder() {
        return new AppasUsefulThings();
    }

    /**
     * Enables build logging. For now this doesn't really do too much.
     * @return Instance of {@link AppasUsefulThings}
     */
    public AppasUsefulThings enableBuildLogging() {
        this.enableBuildLogging = true;
        return this;
    }

    /**
     * Allows you to use {@link GuiManager}.
     * @return Instance of {@link AppasUsefulThings}
     */
    public AppasUsefulThings enableGuiManager() {
        this.enableGuiManager = true;
        return this;
    }


    /**
     * Finishes building your instance.
     * @param plugin An instance of your plugin.
     * @return Instance of {@link AppasUsefulThings}
     */
    public AppasUsefulThings build(JavaPlugin plugin) {
        if (enableGuiManager) {
            guiManager = new GuiManager();
            Bukkit.getPluginManager().registerEvents(guiManager, plugin);
        }

        if (!(enableBuildLogging)) {
            return this;
        }

        Logger logger = Logger.builder()
                .setPrefix(prefix)
                .build(plugin);

        logger.log(Component.text("Appa's Useful Things is enabled."));

        if (enableGuiManager) {
                logger.log(Component.text("Gui Manager successfully enabled")
            );
        }

        return this;
    }
}
