package org.appa.appasUsefulThings.guiManager;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Handles inventory events for a GUI.
 *
 * <p>Events passed to this interface are already bound to the GUI instance.
 * Implementations do not need to check whether an event belongs to their gui.</p>
 *
 * Implementing this interface on a class that **does not** extend {@link Gui} has no effect.
 */
@SuppressWarnings("unused")
public interface InteractiveGui extends Gui {
    default void onOpen(InventoryOpenEvent event) {}
    default void onClose(InventoryCloseEvent event) {}
    default void onInventoryClick(InventoryClickEvent event) {}
    default void onInventoryDrag(InventoryDragEvent event) {}
}
