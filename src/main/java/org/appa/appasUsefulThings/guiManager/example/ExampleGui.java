package org.appa.appasUsefulThings.guiManager.example;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.appa.appasUsefulThings.guiManager.GuiInteractions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ExampleGui implements GuiInteractions {
    final TextComponent title = Component.text("Example GUI")
            .color(NamedTextColor.LIGHT_PURPLE);


    private final Inventory inventory =  Bukkit.createInventory(
            null,
            9*3,
            title
    );

    public ExampleGui() {
        ItemStack border = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, border);
            inventory.setItem(i+18, border);
        }
    }

    @Override
    public String getId() {
        return "ExampleGui";
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        event.getPlayer().sendMessage("Opening Example Gui");
        event.getPlayer().getWorld().playSound(
                event.getPlayer().getLocation(),
                Sound.BLOCK_NOTE_BLOCK_PLING,
                1,
                1
        );
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
