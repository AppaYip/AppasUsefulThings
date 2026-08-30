package org.appa.appasUsefulThings.betterPowerTools;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListeners implements Listener {
    private final BetterPowerTools betterPowerTools;

    public EventListeners(JavaPlugin plugin, BetterPowerTools betterPowerTools) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.betterPowerTools = betterPowerTools;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        PowerToolEventType type;

        // We don't want to fire events for offhands.
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        // Seems to be a lot of edge cases where this can be null. We don't want to fire events when no item exists.
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        if (event.getAction().isRightClick()) {
            type = PowerToolEventType.RIGHT;
        } else if (event.getAction().isLeftClick()) {
            type = PowerToolEventType.LEFT;
        } else {
            return;
        }

        boolean cancel = this.betterPowerTools.dispatch(
                event.getPlayer(),
                item,
                type,
                null
        );

        if (cancel) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        // There has to be a better way to do this...right?
        ItemStack mainItem = event.getMainHandItem();
        if (this.betterPowerTools.hasPowerTool(mainItem)) {
            event.setCancelled(
                    this.betterPowerTools.dispatch(
                            event.getPlayer(),
                            mainItem,
                            PowerToolEventType.SWAP_MAIN_TO_OFFHAND,
                            null
                    )
            );
        }

        ItemStack offItem = event.getOffHandItem();
        if (this.betterPowerTools.hasPowerTool(offItem)) {
            event.setCancelled(
                    this.betterPowerTools.dispatch(
                            event.getPlayer(),
                            offItem,
                            PowerToolEventType.SWAP_OFFHAND_TO_MAIN,
                            null
                    )
            );
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        boolean cancel = this.betterPowerTools.dispatch(
                event.getPlayer(),
                event.getItemDrop().getItemStack(),
                PowerToolEventType.DROP,
                null
        );

        if (cancel) {
            event.setCancelled(true);
        }
    }
}
