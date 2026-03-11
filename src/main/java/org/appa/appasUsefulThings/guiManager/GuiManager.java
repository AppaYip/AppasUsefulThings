package org.appa.appasUsefulThings.guiManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager implements Listener {
    private final Map<String, Gui> guis = new HashMap<>();
    private final Map<UUID, Gui> openGuis = new HashMap<>();

    /**
     * Registers a gui
     * @param gui, The gui
     */
    public void registerGui(Gui gui) {
        if (guis.containsKey(gui.getId())) {
            throw new IllegalStateException(
                    "GUI with id '" + gui.getId()
                    + "' is already registered in this instance of GuiManager '"
            );
        }

        guis.put(gui.getId(), gui);
    }


    /**
     * Removes a gui from registry
     * @param gui, The gui
     */
    public void unregisterGui(Gui gui) {
        guis.remove(gui.getId());
    }

    /**
     * Adds the player to openGuis. This will override any previous gui silently.
     * I may eventually add logging for this.
     * @param player, The player
     * @param gui, The gui
     */
    public void open(Player player, Gui gui) {
        Inventory inventory = gui.getInventory();
        if  (inventory == null) {
            throw new IllegalStateException(
                    "Player '" + player.getName() + "'tried to open GUI with id ' "
                    + gui.getId() + "' but inventory is null"
            );
        }

        openGuis.put(player.getUniqueId(), gui);
        player.openInventory(inventory);
    }

    /**
     * Opens a gui to a player via id
     * @param player, The player
     * @param guiId, The gui id
     */
    public void open(Player player, String guiId) {
        Gui gui = guis.get(guiId);
        if (gui == null) {
            throw new IllegalStateException(
                    "Player '" + player.getName() + "' tried to open GUI with id '"
                    + guiId + "' but gui is null"
            );
        }
        open(player, gui);
    }

    /**
     * This is done automatically and is recommended that you don't do this.
     * Clears the players open gui.
     * @param player, The player
     */
    public void close(Player player) {
        openGuis.remove(player.getUniqueId());
    }

    /**
     * Returns a bool based on if the player has an opened gui. (If opened, true, if not false)
     * @param player, The player
     * @return boolean based on opened gui state
     */
    public boolean isOpen(Player player) {
        return  openGuis.containsKey(player.getUniqueId());
    }


    /*  ============
        LISTENERS
        ============ */

    private GuiInteractions getInteractions(Player player) {
        Gui gui = openGuis.get(player.getUniqueId());
        if (gui instanceof GuiInteractions interactions) return interactions;
        return null;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        GuiInteractions gui = getInteractions(player);
        if (gui == null) return;
        gui.onOpen(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        GuiInteractions gui = getInteractions(player);
        if (gui == null) return;
        gui.onClose(event);

        openGuis.remove(player.getUniqueId());
    }

    /*
     *
     * YO LATER ME. I got no idea what the heck onInteract even does. Listen for events separately
     * Maybe even make own wrapper?
     *
     */

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        GuiInteractions gui = getInteractions(player);
        if (gui == null) return;
        gui.onInventoryClick(event);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        GuiInteractions gui = getInteractions(player);
        if (gui == null) return;
        gui.onInventoryDrag(event);
    }
}
