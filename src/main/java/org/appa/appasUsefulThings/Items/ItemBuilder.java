package org.appa.appasUsefulThings.Items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ItemBuilder {
    private final Material material;

    // Main
    private Component name;
    private int amount = 1;
    private List<Component> lore = new ArrayList<>();;

    // Appearance
    private Integer customModelData = null;
    private ItemFlag[] itemFlags;

    // Misc
    private boolean unbreakable = false;
    private int damage = 0;
    private HashMap<Enchantment, Integer> enchants = new HashMap<>();
    private boolean hideToolTip = false;



    /*
     * Constructors
     */
    public ItemBuilder(Material material, Component name, int damage) {
        this.material = material;
        this.name = name;
        this.damage = damage;
    }

    public ItemBuilder(Material material, Component name) {
        this.material = material;
        this.name = name;
    }

    public ItemBuilder(Material material, int damage) {
        this.material = material;
        this.damage = damage;
    }


    public ItemBuilder(Material material) {
        this.material = material;
    }


    /*
     * Main Things
     * Name, Amount, Lore
     */
    public ItemBuilder setDisplayName(Component name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }


    public ItemBuilder addLore(Component... line) {
        this.lore.addAll(Arrays.asList(line));
        return this;
    }


    /*
     * Appearance
     * Custom Model Data, Item Flags
     */
    public ItemBuilder setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.itemFlags = flags;
        return this;
    }

    /*
     * Misc
     * Unbreakable, Durability, Enchantments
     */

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.enchants.put(enchantment, level);
        return this;
    }

    public ItemBuilder hideToolTip(boolean hideToolTip) {
        this.hideToolTip = hideToolTip;
        return this;
    }


    /*
     * Builder
     */

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material);
        ItemMeta itemMeta = item.getItemMeta();

        // Main things
        if (this.name != null) itemMeta.displayName(this.name);
        item.setAmount(this.amount);
        if (!this.lore.isEmpty()) itemMeta.lore(this.lore);

        // Appearance
        if (customModelData != null) itemMeta.setCustomModelData(customModelData);
        if (this.itemFlags != null) itemMeta.addItemFlags(this.itemFlags);


        // Misc
        itemMeta.setUnbreakable(this.unbreakable);
        if (itemMeta instanceof Damageable) {
            ((Damageable) itemMeta).setDamage(this.damage);
        }
        item.addEnchantments(this.enchants);

        itemMeta.setHideTooltip(this.hideToolTip);

        item.setItemMeta(itemMeta);
        return item;
    }
}
