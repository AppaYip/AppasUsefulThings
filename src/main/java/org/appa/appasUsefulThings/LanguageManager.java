package org.appa.appasUsefulThings;


import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Provides methods for loading and retrieving Adventure {@link Component}s
 * from a YAML language file containing {@link MiniMessage}-formatted strings.
 * This allows you to use <a href="https://docs.papermc.io/adventure/minimessage/format/">MiniMessages</a> directly in the file.
 * <p>
 * Note: Bukkit's {@link YamlConfiguration} api **is** blocking and will stall the main thread.
 * Using methods such as {@link LanguageManager#reload()} will stall the main thread for now.
 * This will *hopefully* change in a future update.
 */
@SuppressWarnings("unused")
public final class LanguageManager {
    private static final String DEFAULT_INVALID_KEY = "<red>Invalid Key <grey>'<white><key><grey>'";
    private static final String INVALID_KEY_PATH = "errors.invalid_key";

    private YamlConfiguration configuration;
    private final MiniMessage miniMessage;
    private final Path filePath;

    /**
     * Gets an instance of {@link LanguageManager}.
     * Upon creation, this reloads the file.
     */
    public LanguageManager(@NonNull Path path) {
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException(
                    "Language file is not a regular file: " + path
            );
        }

        this.filePath = path;
        this.miniMessage = MiniMessage.miniMessage();
        reload();
    }

    /**
     * Reloads the current file.
     *
     * @throws IllegalStateException if the language file could not be loaded.
     */
    public void reload() {
        try {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.load(filePath.toFile());

            this.configuration = configuration;
        } catch (IOException | InvalidConfigurationException exception) {
            throw new IllegalStateException(
                    "Failed to load language file: " + filePath, exception
            );
        }
    }


    /**
     * Gets and deserializes a {@link MiniMessage} from the loaded language file.
     * @param key the YAML key.
     * @param resolvers Tag resolvers used while deserializing the message.
     * @return The value in the file from the key.
     */
    public @NotNull Component get(@NonNull String key, @NonNull TagResolver... resolvers) {
        String value = configuration.getString(key);

        if (value != null) {
            return miniMessage.deserialize(value, resolvers);
        }

        if (!key.equals(INVALID_KEY_PATH)) {
            String error = configuration.getString(INVALID_KEY_PATH);

            if (error != null) {
                return miniMessage.deserialize(
                        error,
                        Placeholder.unparsed("key", key)
                );
            }
        }

        return miniMessage.deserialize(DEFAULT_INVALID_KEY, Placeholder.unparsed("key", key));
    }

    /**
     * Gets and deserializes a {@link MiniMessage} from the loaded language file.
     * @param key the YAML key.
     * @return The value in the file from the key.
     */
    public @NotNull Component get(@NonNull String key) {
        return get(key, TagResolver.empty());
    }
}
