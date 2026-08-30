package org.appa.appasUsefulThings;

import lombok.Getter;
import lombok.NonNull;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings {
    @Getter private static JavaPlugin plugin;
    private static GuiManager guiManager;

    public AppasUsefulThings(@NonNull JavaPlugin plugin) {
        AppasUsefulThings.plugin = plugin;
    }

    /**
     * Gets an instance of {@link GuiManager}. This lets you use Gui Elements.
     *
     * @return An instance of {@link GuiManager}. This **will** use the same instance every time this is called.
     */
    public static GuiManager getGuiManager() {
        if (guiManager == null) {
            guiManager = new GuiManager();
            Bukkit.getPluginManager().registerEvents(guiManager, plugin);
        }

        return guiManager;
    }
}
