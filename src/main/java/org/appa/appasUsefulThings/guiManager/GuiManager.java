package org.appa.appasUsefulThings.guiManager;

import lombok.NonNull;
import org.appa.appasUsefulThings.AppasUsefulThings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class allows for registering a gui, opening/closing a gui, and many more things.
 * Each method has Javadocs, and it is encouraged that you read them.
 */
@SuppressWarnings("unused")
public class GuiManager implements Listener {
    private final Map<String, Gui> guis = new HashMap<>();
    private final Map<UUID, Gui> openGuis = new HashMap<>();

    /**
     * This class should not be instantized by another plugin.
     * This is meant for internal usage only.
     *
     * <p>If you are trying to use the gui manager, see {@link AppasUsefulThings#getGuiManager()}.</p>
     */
    @ApiStatus.Internal
    public GuiManager() {}

    /**
     * Registers a gui. See {@link Gui} for more information.
     * @param gui The gui
     */
    public void registerGui(@NonNull Gui gui) {
        registerGui(gui, false);
    }

    /**
     * Registers a gui. See {@link Gui} for more information.
     * @param overwrite Whether to override an already existing gui.
     */
    public void registerGui(@NonNull Gui gui, boolean overwrite) {
        if (overwrite) {
            guis.put(gui.getId(), gui);
            return;
        }

        if (guis.containsKey(gui.getId())) {
            throw new IllegalStateException(
                "GUI with id '%s' is already registered in this instance of GuiManager".formatted(gui.getId())
            );
        }
        guis.put(gui.getId(), gui);
    }


    /**
     * Removes a {@link Gui} from registry.
     * @param gui The gui
     */
    public void unregisterGui(Gui gui) {
        guis.remove(gui.getId());
    }

    /**
     * Opens a {@link Gui} to a player. This is required to route events to your gui.
     * @param player The player
     * @param gui An instance of a {@link Gui}
     */
    public void open(@NonNull Player player, @NonNull Gui gui) {
        Inventory inventory = gui.getInventory();

        // Paper sucks. Why can't I get the title of an inventory using its object without using bukkit internals.
        Inventory clone = Bukkit.createInventory(null, inventory.getSize(), gui.getTitle());

        ItemStack[] content = inventory.getContents();
        for (int i = 0; i < content.length; i++) {
            ItemStack item = content[i];
            if (item == null) continue;
            clone.setItem(i, item.clone());
        }

        player.openInventory(clone);
        openGuis.put(player.getUniqueId(), gui);
    }

    /**
     * Opens a {@link Gui} to a player. This is required to route events to your gui.
     * @param player The player
     * @param guiId The id of a {@link Gui}
     */
    public void open(@NonNull Player player, @NonNull String guiId) {
        Gui gui = guis.get(guiId);
        if (gui == null) {
            throw new IllegalStateException(
                "Player '%s' tried to open GUI with id '%s' but gui is null".formatted(
                        player.getName(), guiId
                )
            );
        }
        open(player, gui);
    }

    /**
     * Clears the players open {@link Gui}.
     * This does automatically and typically does not need to be invoked.
     * <p>
     * This **will not** trigger if the reason for closing is opening another gui.
     * This is done to allow gui editing without breaking event routing.
     * @param player The player
     */
    public void close(Player player) {
        openGuis.remove(player.getUniqueId());
    }

    /**
     * Checks if a player has a gui opened.
     * @param player The player
     * @return whether the player has a {@link Gui} opened.
     */
    public boolean isOpen(Player player) {
        return openGuis.containsKey(player.getUniqueId());
    }

    /* Events */

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        if (openGuis.get(player.getUniqueId()) instanceof InteractiveGui gui) {
            gui.onOpen(event);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (event.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) return;

        if (openGuis.get(player.getUniqueId()) instanceof InteractiveGui gui) {
            gui.onClose(event);
        }
        openGuis.remove(player.getUniqueId());
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (openGuis.get(player.getUniqueId()) instanceof InteractiveGui gui) {
            gui.onInventoryClick(event);
        }
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (openGuis.get(player.getUniqueId()) instanceof InteractiveGui gui) {
            gui.onInventoryDrag(event);
        }
    }
}
