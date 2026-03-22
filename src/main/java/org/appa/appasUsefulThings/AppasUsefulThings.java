package org.appa.appasUsefulThings;

import org.appa.appasUsefulThings.commands.ConfigCommand;
import org.appa.appasUsefulThings.config.Config;
import org.appa.appasUsefulThings.cooldowns.example.ExampleCooldownCommand;
import org.appa.appasUsefulThings.guiManager.GuiManager;
import org.appa.appasUsefulThings.guiManager.example.ExampleGuiCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.appa.appasUsefulThings.config.Config.checkConfig;


public final class  AppasUsefulThings extends JavaPlugin {
    public static AppasUsefulThings plugin;
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static Logger logger;
    public static Logger getMyLogger() {
        return logger;
    } // Horrid name

    public static File configFile;
    public static File getConfigFile() {
        return configFile;
    }

    public static YamlConfiguration config;
    public YamlConfiguration getConfig() {
        return config;
    }

    public static GuiManager guiManager;
    public static GuiManager getGuiManager() {
        return guiManager;
    }


    @Override
    public void onEnable() {
        plugin = this;
        logger = new Logger(this);
        guiManager = new GuiManager();
        logger.log("Appa's useful things has loaded!");


        Bukkit.getPluginManager().registerEvents(
                guiManager, this
        );

        new ConfigCommand().register(this);
        new ExampleGuiCommand().regsiter(this);
        new ExampleCooldownCommand().register(this);


        configFile = new File(getDataFolder(), "config.yml");
        this.saveResource("config.yml", false);
        config = Config.create(this, configFile, false);

        checkConfig();
    }
}
