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
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * A builder to make creating items simple. Methods have Javadocs, however they should be self-explanatory.
 */
@SuppressWarnings({"UnstableApiUsage", "unused"})
public class ItemBuilder {
    private ItemBuilder() {
        // Utility Class
    }

    /**
     * Creates an item using the applied builder.
     *
     * @param material The material of the item.
     * @param consumer The item configuration.
     * @return the built item.
     */
    public static ItemStack of(@NonNull Material material, @NonNull Consumer<Item> consumer) {
        return new Item(material)
                .apply(consumer)
                .build();
    }


    @SuppressWarnings("UnusedReturnValue")
    public static final class Item {
        private final Material material;

        private Component name;
        private int amount = 1;
        private final List<Component> lore = new ArrayList<>();

        // Misc
        private boolean unbreakable;
        private int damage;
        private boolean hideToolTip;

        private final Appearance appearance = new Appearance();
        private final Enchantments enchantments = new Enchantments();


        private Item(@NonNull Material material) {
            this.material = material;
        }

        private Item apply(@NonNull Consumer<Item> consumer) {
            consumer.accept(this);
            return this;
        }


        /**
         * Sets the name of the item. Internally this will make a component.
         * @param name The {@link String} to set the item to.
         */
        public Item name(@NonNull String name) {
            this.name = Component.text(name);
            return this;
        }

        /**
         * Sets the name of the item.
         * @param name The {@link Component} to set the name to.
         */
        public Item name(@NonNull Component name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the count of the item.
         * @param amount The amount of the item.
         */
        public Item amount(int amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Adds lore to the item.
         * @param lore The {@link String}s to add to the item's lore.
         */
        public Item lore(@NonNull String... lore) {
            for (String l : lore) {
                this.lore.add(Component.text(l));
            }
            return this;
        }

        /**
         * Adds lore to the item.
         * @param lines The {@link Component}s to add to the item's lore.
         */
        public Item lore(@NonNull Component... lines) {
            this.lore.addAll(Arrays.asList(lines));
            return this;
        }

        /**
         * Creates an appearance object.
         * @param appearance The configuration for the item's appearance.
         */
        public Item appearance(@NonNull Consumer<Appearance> appearance) {
            appearance.accept(this.appearance);
            return this;
        }

        /**
         *  Creates an enchantment object.
         * @param consumer Enchantment configuration.
         */
        public Item enchantments(@NonNull Consumer<Enchantments> consumer) {
            consumer.accept(this.enchantments);
            return this;
        }

        /**
         * Makes the item unbreakable.
         */
        public Item unbreakable() {
            this.unbreakable = true;
            return this;
        }

        /**
         * Sets the damage to the item.
         * @param damage The amount of damage.
         */
        public Item damage(int damage) {
            this.damage = damage;
            return this;
        }

        /**
         * Hides the tooltip of the item.
         */
        public Item hideToolTip() {
            this.hideToolTip = true;
            return this;
        }

        public ItemStack build() {
            ItemStack item = new ItemStack(this.material);
            ItemMeta meta = item.getItemMeta();

            applyBasicProperties(meta);
            applyBasicAppearance(meta);
            applyMiscProperties(meta);

            item.setAmount(this.amount);
            item.setItemMeta(meta);

            return item;
        }

        private void applyBasicProperties(@NonNull ItemMeta meta) {
            if (this.name != null) {
                meta.displayName(this.name);
            }

            if (!this.lore.isEmpty()) {
                meta.lore(this.lore);
            }
        }

        private void applyBasicAppearance(@NonNull ItemMeta meta) {
            if (this.appearance.itemFlags.length > 0) {
                meta.addItemFlags(this.appearance.itemFlags);
            }

            CustomModelDataComponent customModelData =
                    meta.getCustomModelDataComponent();

            customModelData.setFloats(this.appearance.modelData.customModelDataFloats);
            customModelData.setFlags(this.appearance.modelData.customModelDataFlags);
            customModelData.setStrings(this.appearance.modelData.customModelDataStrings);
            customModelData.setColors(this.appearance.modelData.customModelDataColors);

            meta.setCustomModelDataComponent(customModelData);
        }

        private void applyMiscProperties(@NonNull ItemMeta meta) {
            meta.setUnbreakable(this.unbreakable);
            meta.setHideTooltip(this.hideToolTip);

            if (meta instanceof Damageable damageable) {
                damageable.setDamage(this.damage);
            }

            if (!this.enchantments.enchants.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> entry : enchantments.enchants.entrySet()) {
                    meta.addEnchant(
                            entry.getKey(),
                            entry.getValue(),
                            true
                    );
                }
            }
        }


        // Appearance Object
        public static final class Appearance {
            private ItemFlag[] itemFlags = new ItemFlag[0];
            private final ModelData modelData = new ModelData();

            public Appearance flags(@NonNull ItemFlag... itemFlags) {
                this.itemFlags = itemFlags;
                return this;
            }

            public Appearance modelData(@NonNull Consumer<ModelData> consumer) {
                consumer.accept(this.modelData);
                return this;
            }
        }

        public static final class ModelData {
            private final List<Float> customModelDataFloats = new ArrayList<>();
            private final List<Boolean> customModelDataFlags = new ArrayList<>();
            private final List<String> customModelDataStrings = new ArrayList<>();
            private final List<Color> customModelDataColors = new ArrayList<>();

            public ModelData floats(Float... floats) {
                this.customModelDataFloats.addAll(Arrays.asList(floats));
                return this;
            }

            public ModelData flags(Boolean... flags) {
                this.customModelDataFlags.addAll(Arrays.asList(flags));
                return this;
            }

            public ModelData strings(String... strings) {
                this.customModelDataStrings.addAll(Arrays.asList(strings));
                return this;
            }

            public ModelData colors(Color... color) {
                this.customModelDataColors.addAll(Arrays.asList(color));
                return this;
            }
        }


        // Enchants Object
        public static final class Enchantments {
            private final Map<Enchantment, Integer> enchants = new HashMap<>();

            public Enchantments add(@NonNull Enchantment enchant, int level) {
                this.enchants.put(enchant, level);
                return this;
            }
        }
    }
}
