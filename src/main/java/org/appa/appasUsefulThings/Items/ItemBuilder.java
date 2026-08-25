package org.appa.appasUsefulThings.Items;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jspecify.annotations.NullMarked;

import java.util.*;

/**
 * A builder to make creating items simple. Methods have Javadocs, however they should be self-explanatory.
 */
@NullMarked
@SuppressWarnings("unused")
public class ItemBuilder {
    private final Material material;

    // Main
    private Component name = Component.empty();
    private int amount = 1;
    private final List<Component> lore = new ArrayList<>();

    // Appearance
    private ItemFlag[] itemFlags = new ItemFlag[0];

    private final List<Float> customModelDataFloats = new ArrayList<>();
    private final List<Boolean> customModelDataFlags = new ArrayList<>();
    private final List<String> customModelDataStrings = new ArrayList<>();
    private final List<Color> customModelDataColors = new ArrayList<>();

    // Misc
    private boolean unbreakable = false;
    private int damage = 0;
    private final Map<Enchantment, Integer> enchants = new HashMap<>();
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
     * Appends floats to the item's custom model data.
     * @param values floats to add to the item's custom model data.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder customModelData(float... values) {
        for (float value : values) {
            this.customModelDataFloats.add(value);
        }
        return this;
    }

    /**
     * Appends booleans to the item's custom model data.
     * @param values booleans to add to the item's custom model data.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder customModelData(boolean... values) {
        for (boolean value : values) {
            this.customModelDataFlags.add(value);
        }
        return this;
    }

    /**
     * Appends strings to the item's custom model data.
     * @param values strings to add to the item's custom model data.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder customModelData(String... values) {
        this.customModelDataStrings.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Appends floats to the item's custom model data.
     * @param values floats to add to the item's custom model data.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder customModelData(Color... values) {
        this.customModelDataColors.addAll(Arrays.asList(values));
        return this;
    }


    /**
     * Adds item flags to the item.
     * @param flags The flag[s] to add.
     * @return Instance of the ItemBuilder.
     */
    public ItemBuilder setItemFlags(ItemFlag... flags) {
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
        if (!this.name.equals(Component.empty())) itemMeta.displayName(this.name);

        item.setAmount(this.amount);
        if (!this.lore.isEmpty()) itemMeta.lore(this.lore);

        // Appearance
        if (this.itemFlags.length > 0) itemMeta.addItemFlags(this.itemFlags);

        CustomModelDataComponent customModelDataComponent = itemMeta.getCustomModelDataComponent();

        customModelDataComponent.setFloats(this.customModelDataFloats);
        customModelDataComponent.setFlags(this.customModelDataFlags);
        customModelDataComponent.setStrings(this.customModelDataStrings);
        customModelDataComponent.setColors(this.customModelDataColors);

        itemMeta.setCustomModelDataComponent(customModelDataComponent);


        // Misc
        itemMeta.setUnbreakable(this.unbreakable);
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(this.damage);
        }
        item.addEnchantments(this.enchants);

        itemMeta.setHideTooltip(this.hideToolTip);

        item.setItemMeta(itemMeta);
        return item;
    }
}
