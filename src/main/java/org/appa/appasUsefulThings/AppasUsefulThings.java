package org.appa.appasUsefulThings;

import lombok.NonNull;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings {
    public AppasUsefulThings(@NonNull JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new GuiManager(), plugin);
    }
}
