package org.appa.appasUsefulThings.config;

import org.appa.appasUsefulThings.AppasUsefulThings;
import org.appa.appasUsefulThings.cooldowns.example.ExCooldownCommand;
import org.appa.appasUsefulThings.guiManager.example.ExampleGui;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.appa.appasUsefulThings.AppasUsefulThings.config;

public class Config {

    public static YamlConfiguration create(JavaPlugin plugin, File file, boolean replace) {
        if (!file.exists() || replace) {
            plugin.saveResource(file.getName(), true);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    static GuiManager guiManager = AppasUsefulThings.getGuiManager();

    public static void checkConfig() {
        ExampleGui exampleGui = new ExampleGui();

        if (config.getBoolean("GuiRoot.ExampleGuiEnabled")) {
            guiManager.registerGui(exampleGui);
        } else  {
            guiManager.unregisterGui(exampleGui);
        }
    }
}
