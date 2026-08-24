package org.appa.appasUsefulThings.guiManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiListener implements Listener {

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
            openGuis.remove(player.getUniqueId());
        }
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
