package org.appa.appasUsefulThings.betterPowerTools;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PlayerSettingsManager {
    private final NamespacedKey key;

    public PlayerSettingsManager(@NotNull JavaPlugin plugin) {
        this.key = new NamespacedKey(plugin, "betterpowertools_toggle");
    }

    /**
     * Returns if the can use power tools.
     * This defaults to true.
     * @param player The player.
     * @return whether player can use power tools.
     */
    public boolean isEnabled(Player player) {
        return player.getPersistentDataContainer().getOrDefault(this.key, PersistentDataType.BOOLEAN, true);
    }

    /**
     * Toggles player's power tool enable state.
     * @param player The player.
     */
    public boolean toggle(Player player) {
        boolean enabled = !isEnabled(player);
        setEnabled(player, enabled);
        return enabled;
    }

    /**
     * Sets if the player can use power tools.
     * @param player The player.
     * @param enabled Whether player can use power tools.
     */
    public void setEnabled(Player player, boolean enabled) {
        player.getPersistentDataContainer().set(this.key, PersistentDataType.BOOLEAN, enabled);
    }

    /**
     * Clears the player's power tool toggle preference.
     * @param player The player.
     */
    @SuppressWarnings("unused")
    public void clear(Player player) {
        player.getPersistentDataContainer().remove(this.key);
    }
}
