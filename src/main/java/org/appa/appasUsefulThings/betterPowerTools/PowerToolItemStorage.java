package org.appa.appasUsefulThings.betterPowerTools;


import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PowerToolItemStorage {
    private final NamespacedKey key;

    public PowerToolItemStorage(@NotNull JavaPlugin plugin) {
        this.key = new  NamespacedKey(plugin, "BetterPowerTools");
    }

    /**
     * Stores power tool ID on ItemStack using PDC.
     * The provided ItemStack is mutated.
     * @param itemStack The item.
     * @param id The id.
     */
    public void bindId(ItemStack itemStack, String id) {
        itemStack.editMeta(meta ->
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id));
    }

    /**
     * Gets the callback id.
     * @return The callback id.
     */
    public Optional<String> getId(ItemStack itemStack) {
        return Optional.ofNullable(
                itemStack.getPersistentDataContainer().get(key, PersistentDataType.STRING)
        );
    }

    /**
     * @param itemStack The itemstack.
     * @return whether the itemstack ahs a callback id.
     */
    public boolean hasId(ItemStack itemStack) {
        return itemStack.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    /**
     * Removes the callback id from an item.
     * @param itemStack The item.
     */
    public void clearId(ItemStack itemStack) {
        itemStack.editMeta(meta ->
                meta.getPersistentDataContainer().remove(key));
    }
}
