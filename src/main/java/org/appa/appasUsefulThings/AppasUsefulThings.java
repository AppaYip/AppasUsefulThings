package org.appa.appasUsefulThings;

import lombok.Getter;
import lombok.NonNull;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings {
    @Getter private static JavaPlugin plugin;

    public AppasUsefulThings(@NonNull JavaPlugin plugin) {
        AppasUsefulThings.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new GuiManager(), plugin);
    }
}
