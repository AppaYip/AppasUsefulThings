package org.appa.appasUsefulThings.guiManager;

import org.bukkit.event.inventory.*;
import org.jspecify.annotations.NullMarked;

/**
 * {@inheritDoc}
 * Events from this interface are bound to the specific gui. You do not need to check for your gui.
 */
@NullMarked
@SuppressWarnings("unused")
public interface GuiInteractions extends Gui {
    default void onOpen(InventoryOpenEvent event) {}
    default void onClose(InventoryCloseEvent event) {}
    default void onInventoryClick(InventoryClickEvent event) {}
    default void onInventoryDrag(InventoryDragEvent event) {}
}
