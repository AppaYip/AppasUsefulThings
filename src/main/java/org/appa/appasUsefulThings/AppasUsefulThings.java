package org.appa.appasUsefulThings;

import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AppasUsefulThings extends JavaPlugin {
    public AppasUsefulThings() {
        Bukkit.getPluginManager().registerEvents(new GuiManager(), this);
    }
}
