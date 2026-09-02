package org.appa.appasUsefulThings.betterPowerTools;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

/**
 * Main entry for Better Power Tools.
 *
 * <p>This class provides convenience methods for registering power tools,
 * binding them to items, managing player settings, and executing registered
 * callbacks.</p>
 *
 * <p>For advanced functionality, the underlying managers can be accessed
 * through their respective getter methods.</p>
 */
@SuppressWarnings("unused")
public abstract class BetterPowerTools {
    @Getter private final PowerToolRegistry powerToolRegistry;
    @Getter private final PowerToolItemStorage itemStorage;
    @Getter private final PlayerSettingsManager playerSettingsManager;

    public BetterPowerTools(JavaPlugin plugin) {
        this.powerToolRegistry = new PowerToolRegistry();
        this.itemStorage = new PowerToolItemStorage(plugin);
        this.playerSettingsManager = new PlayerSettingsManager(plugin);
        new EventListeners(plugin, this);
    }


    /* Player Settings */

    /**
     * Checks if the player has Better Power Tools enabled.
     *
     * @param player The player.
     * @return Whether the play can use Power Tools.
     */
    public boolean isEnabled(Player player) {
        return playerSettingsManager.isEnabled(player);
    }

    /**
     * Toggles the player's state for using Power Tools.
     * If this returns false, power tool events **will not** fire for the player.
     *
     * @param player The player.
     * @return Whether the player can use Power Tools.
     */
    public boolean toggle(Player player) {
        return playerSettingsManager.toggle(player);
    }

    /**
     * Sets the player's state for using Power Tools.
     *
     * @param player The player.
     * @param state The state.
     */
    public void setEnabled(Player player, boolean state) {
        playerSettingsManager.setEnabled(player, state);
    }

    /* ItemManager */

    /**
     * Binds an ItemStack to a PowerTool id.
     * This will modify the inputted ItemStack.
     *
     * @param itemStack The ItemStack.
     * @param id The id.
     */
    public void bind(ItemStack itemStack, String id) {
        itemStorage.bindId(itemStack, id);
    }

    /**
     * Removes a PowerTool id from an ItemStack.
     * This will modify the inputted ItemStack.
     *
     * @param itemStack The ItemStack.
     */
    public void clear(ItemStack itemStack) {
        itemStorage.clearId(itemStack);
    }

    /**
     * Gets the PowerTool id from an ItemStack.
     *
     * @param itemStack The ItemStack.
     * @return Items aren't guaranteed to have an id bound to them, so this returns an optional.
     */
    public Optional<String> getId(ItemStack itemStack) {
        return itemStorage.getId(itemStack);
    }

    /**
     * Checks whether the provided ItemStack has a PowerTool id.
     *
     * @param itemStack The ItemStack.
     * @return Whether the ItemStack has an id.
     */
    public boolean hasPowerTool(ItemStack itemStack) {
        return itemStorage.hasId(itemStack);
    }

    /* PowerTool Register */

    /**
     * Registers a new PowerTool.
     *
     * @param id The id.
     * @param tool The PowerTool Call Back.
     * @throws IllegalStateException If there is an existing PowerTool registered under the id.
     */
    public void register(String id, PowerTool tool) {
        powerToolRegistry.registerIfAbsent(id, tool);
    }

    /**
     * Unregisters a PowerTool.
     *
     * @param id The id.
     */
    public void unregister(String id) {
        powerToolRegistry.unregister(id);
    }

    /**
     * Gets the PowerTools callback.
     * This is mostly for internal usage.
     *
     * @param id The id.
     * @return There is no guaranteed a callback will exist under the id, so we return an optional.
     */
    public Optional<PowerTool> get(String id) {
        return powerToolRegistry.get(id);
    }

    /**
     * Checks if a PowerTool callback exists.
     *
     * @param id The id.
     * @return Whether a PowerTool callback exists.
     */
    public boolean exists(String id) {
        return powerToolRegistry.exists(id);
    }

    /* Event Handler */

    /**
     * This is mostly for Internal usage.
     *
     * @param player The player using the PowerTool.
     * @param item The PowerTool.
     * @param type The type of PowerTool.
     * @param eventData Extra Data
     * @return Whether the Bukkit event should be canceled.
     */
    public <T> boolean dispatch (
            Player player,
            ItemStack item,
            PowerToolEventType type,
            T eventData
    ) {
        if (!playerSettingsManager.isEnabled(player)) {
            return false;
        }

        Optional<String> id = itemStorage.getId(item);
        if (id.isEmpty()) {
            return false;
        }

        Optional<PowerTool> tool = powerToolRegistry.get(id.get());
        if (tool.isEmpty()) {
            return false;
        }

        PowerTool powerTool = tool.get();

        PowerToolContext<T> ctx = new PowerToolContext<>(player, item, type, eventData);

        return powerTool.execute(ctx);
    }
}
