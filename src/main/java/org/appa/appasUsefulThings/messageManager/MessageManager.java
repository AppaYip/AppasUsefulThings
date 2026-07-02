package org.appa.appasUsefulThings.messageManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//@SuppressWarnings("unused")
public class MessageManager {
    private final Map<String, String> messages = new HashMap<>();

    private File langFile;

    public MessageManager(File file) {
        this.langFile = file;
    }

    public void reload(File file) {
        messages.clear();
        load(file);
    }

    private void load(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadSection(config, "");
    }

    private void loadSection(ConfigurationSection section, String path) {
        for (String key : section.getKeys(false)) {
            String fullpath;
            if (path.isEmpty()) {
                fullpath = key;
            } else {
                fullpath = path + "." + key;
            }

            Object value = section.get(fullpath);

            if (value instanceof ConfigurationSection child) {
                loadSection(child, fullpath);
            } else if (value instanceof String string) {
                messages.put(fullpath, string);
            }
        }
    }
}
