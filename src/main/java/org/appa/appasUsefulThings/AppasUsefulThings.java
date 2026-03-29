package org.appa.appasUsefulThings;

import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class  AppasUsefulThings extends JavaPlugin {
    private static Logger logger;
    private static GuiManager guiManager;

    public static void initalize(JavaPlugin plugin) {
        logger = new Logger(plugin);
        guiManager = new GuiManager();
        logger.log("Appa's Useful Things has Loaded!");

        Bukkit.getPluginManager().registerEvents(
                guiManager, plugin
        );
    }
}
