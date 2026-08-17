package org.appa.appasUsefulThings.Items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A builder to make creating items simple. Methods should be self-explanatory
 */
@NullMarked
@SuppressWarnings("unused")
public class ItemBuilder {
    private final Material material;

    // Main
    @Nullable private Component name = null;
    private int amount = 1;
    private final List<Component> lore = new ArrayList<>();

    // Appearance
    private ItemFlag[] itemFlags = new ItemFlag[0];

    // Misc
    private boolean unbreakable = false;
    private int damage = 0;
    private final HashMap<Enchantment, Integer> enchants = new HashMap<>();
    private boolean hideToolTip = false;



    /* Constructors */

    /**
     * ItemBuilder entry point.
     * @param material The material to use for the item.
     * @param name The name of the item.
     * @param damage The damage to the item.
     */
    public ItemBuilder(Material material, Component name, int damage) {
        this.material = material;
        this.name = name;
        this.damage = damage;
    }

    /**
     * ItemBuilder entry point.
     * @param material The material to use for the item.
     * @param name The name of the item.
     */
    public ItemBuilder(Material material, Component name) {
        this.material = material;
        this.name = name;
    }

    /**
     * ItemBuilder entry point.
     * @param material The material to use for the item.
     * @param damage The damage to the item.
     */
    public ItemBuilder(Material material, int damage) {
        this.material = material;
        this.damage = damage;
    }

    /**
     * ItemBuilder entry point.
     * @param material The material to use for the item.
     */
    public ItemBuilder(Material material) {
        this.material = material;
    }


    /* Main Things: Name, Amount, Lore */

    /**
     * Sets the display name for the current item.
     * @param name The name of the item.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder setDisplayName(Component name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the stack size of the item.
     * @param amount The amount to set to.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Adds lore to the item.
     * @param line The line[s] to add.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder addLore(Component... line) {
        this.lore.addAll(Arrays.asList(line));
        return this;
    }


    /* Appearance: Custom Model Data, Item Flags */


    /**
     * Adds item flags to the item.
     * @param flags The flag[s] to add.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.itemFlags = flags;
        return this;
    }

    /* Misc: Unbreakable, Durability, Enchantments */

    /**
     * Makes the item unbreakable.
     * @param unbreakable Whether to make the item unbreakable.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Sets the item's damage.
     * @param damage The damage.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Adds an enchantment to the item.
     * @param enchantment The enchantment to add.
     * @param level The level of enchantment.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.enchants.put(enchantment, level);
        return this;
    }

    /**
     * Hides the tooltip of the item.
     * @param hideToolTip Whether to hide the tooltip of the item.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder hideToolTip(boolean hideToolTip) {
        this.hideToolTip = hideToolTip;
        return this;
    }


    /* Builder */

    /**
     * Builds the final item.
     * @return The final {@link ItemStack}
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(this.material);
        ItemMeta itemMeta = item.getItemMeta();

        // Main things
        if (this.name != null) itemMeta.displayName(this.name);
        item.setAmount(this.amount);
        if (!this.lore.isEmpty()) itemMeta.lore(this.lore);

        // Appearance
        if (this.itemFlags.length == 0) itemMeta.addItemFlags(this.itemFlags);


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
