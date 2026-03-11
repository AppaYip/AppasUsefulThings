package org.appa.appasUsefulThings.guiManager;

import org.bukkit.event.inventory.*;


public interface GuiInteractions extends Gui {
    default void onOpen(InventoryOpenEvent event) {}
    default void onClose(InventoryCloseEvent event) {}
    default void onInventoryClick(InventoryClickEvent event) {}
    default void onInventoryDrag(InventoryDragEvent event) {}
}

