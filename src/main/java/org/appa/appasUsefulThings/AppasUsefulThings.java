package org.appa.appasUsefulThings;

import lombok.NonNull;
import org.appa.appasUsefulThings.betterPowerTools.BetterPowerTools;
import org.appa.appasUsefulThings.cooldownManager.CooldownManager;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class AppasUsefulThings {
    private static JavaPlugin plugin;
    private static GuiManager guiManager;
    private static BetterPowerTools betterPowerTools;
    private static CooldownManager cooldownManager;

    private AppasUsefulThings() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }


    /**
     * Initializes AppaUsefulThings. This is required to use other methods.
     *
     * @param plugin An instance of your plugin.
     */
    public static void initialize(@NonNull JavaPlugin plugin) {
        if (AppasUsefulThings.plugin != null) {
            throw new IllegalStateException("AppasUsefulThings has already been initialized.");
        }

        AppasUsefulThings.plugin = plugin;
    }


    /**
     * Gets an instance of {@link GuiManager}. This lets you use Gui Elements.
     * This will return the same instance every call.
     *
     * @return An instance of {@link GuiManager}.
     */
    public static @NotNull  GuiManager getGuiManager() {
        ensureInit();

        if (guiManager == null) {
            guiManager = new GuiManager();
            Bukkit.getPluginManager().registerEvents(guiManager, plugin);
        }

        return guiManager;
    }

    /**
     * Gets an instance of {@link BetterPowerTools}. This will automatically register events.
     * This will return the same instance every call.
     *
     * @return An instance of {@link BetterPowerTools}
     */
    public static @NotNull BetterPowerTools getBetterPowerTools() {
        ensureInit();

        if (betterPowerTools == null) {
            betterPowerTools = new BetterPowerTools(plugin);
        }

        return betterPowerTools;
    }

    /**
     * Gets an instance of {@link CooldownManager}.
     * This will return the same instance every call.
     *
     * @return An instance of {@link CooldownManager}
     */
    public static @NotNull CooldownManager getCooldownManager() {
        if (cooldownManager == null) {
            cooldownManager = new CooldownManager();
        }

        return cooldownManager;
    }

    private static void ensureInit() {
        if (plugin == null) {
            throw new IllegalStateException(
                    "AppasUsefulThings plugin has not been initialized yet." +
                    "Call AppasUsefulThings.initalize(plugin) first."
            );
        }
    }
}
