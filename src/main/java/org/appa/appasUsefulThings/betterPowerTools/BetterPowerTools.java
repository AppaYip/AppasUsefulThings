package org.appa.appasUsefulThings.betterPowerTools;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
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
public abstract class BetterPowerTools {
    @Getter private final PowerToolRegistry powerToolRegistry;
    @Getter private final PowerToolItemStorage itemStorage;
    @Getter private final PlayerSettingsManager playerSettingsManager;

    private final JavaPlugin plugin;

    public BetterPowerTools(JavaPlugin plugin) {
        this.plugin = plugin;
        this.powerToolRegistry = new PowerToolRegistry();
        this.itemStorage = new PowerToolItemStorage(plugin);
        this.playerSettingsManager = new PlayerSettingsManager(plugin);
        new EventListeners(plugin, this);
    }

    /* Config */

    /**
     * Whether to enable a debug command. TODO: Better docs.
     */
    public void enableCommand() {
        this.plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
                commands.registrar().register(
                        new BetterPowerToolsCommand(this).createCommand()
                ));
    }

    /* Player Settings */

    public boolean isEnabled(Player player) {
        return playerSettingsManager.isEnabled(player);
    }

    public boolean toggle(Player player) {
        return playerSettingsManager.toggle(player);
    }

    public void setEnabled(Player player, boolean enabled) {
        playerSettingsManager.setEnabled(player, enabled);
    }

    /* ItemManager */

    public void bind(ItemStack itemStack, String id) {
        itemStorage.bindId(itemStack, id);
    }

    public void clear(ItemStack itemStack) {
        itemStorage.clearId(itemStack);
    }

    public Optional<String> getId(ItemStack itemStack) {
        return itemStorage.getId(itemStack);
    }

    public boolean hasPowerTool(ItemStack itemStack) {
        return itemStorage.hasId(itemStack);
    }

    /* Power Tool Register */

    public void register(String id, PowerTool tool) {
        powerToolRegistry.registerIfAbsent(id, tool);
    }

    public void unregister(String id) {
        powerToolRegistry.unregister(id);
    }

    public Optional<PowerTool> get(String id) {
        return powerToolRegistry.get(id);
    }

    public boolean exists(String id) {
        return powerToolRegistry.exists(id);
    }

    /* Event Handler */
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
