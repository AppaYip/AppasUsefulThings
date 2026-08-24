package org.appa.appasUsefulThings.guiManager;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Events from this interface are bound to the specific gui. You do not need to check for your gui.
 */
@SuppressWarnings("unused")
public abstract class InteractiveGui extends Gui {
    public abstract void onOpen(InventoryOpenEvent event);
    public abstract void onClose(InventoryCloseEvent event);
    public abstract void onInventoryClick(InventoryClickEvent event);
    public abstract void onInventoryDrag(InventoryDragEvent event);
}
