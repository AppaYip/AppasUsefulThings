# Item Builder

This is a builder designed to remove some of the headache when trying to make items.

## Constructors

All constructors require a 'Material'. Name and damage can optionally be passed in, or set later via respective methods, see below.

```java
new ItemBuilder(Material material)
new ItemBuilder(Material material, Component name)
new ItemBuilder(Material material, int damage)
new ItemBuilder(Material material, Component name, int damage)
```

## Usage

All methods return the builder instance, allowing them to be chained together.
Call 'build()' at the end to produce the final 'ItemStack'.

## Simple Items

```java
ItemStack item = new ItemBuilder(Material.DIAMOND)
    .setDisplayName(Component.text("Le Shiny Diamond").color(NamedTextColor.AQUA))
    .build();
```

### Item with lore

```java
ItemStack item = new ItemBuilder(Material.APPLE)
    .setDisplayName(Component.text("First Apple").color(NamedTextColor.RED))
    .addLore(
        Component.text("They say giving it to a sky bison can make a friendship that lasts a life time").color(NamedTextColor.WHITE),
        Component.text("(What, were you expecting a bible reference?)").color(NamedTextColor.GRAY)
    )
    .build();
```

### Enchanted item

```java
ItemStack item = new ItemBuilder(Material.SADDLE)
    .setDisplayName(Component.text("A simple saddle...").color(NamedTextColor.GRAY))
    .enchant(Enchantment.UNBREAKING, 3)
    .addItemFlags(ItemFlag.HIDE_ENCHANTS)
    .setUnbreakable(true)
    .addLore(
        Component.text("Yip. Yip.").color(NamedTextColor(NamedTextColor.GRAY)),
        Component.text("(What does it mean?)")
    )
    .build();
```

### GUI border item

```java
ItemStack border = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
    .setDisplayName(Component.text(""))
    .build();
```

### Custom model data

```java
ItemStack wand = new ItemBuilder(Material.STICK)
    .setDisplayName(Component.text("Magic Wand").color(NamedTextColor.LIGHT_PURPLE))
    .setCustomModelData(1)
    .addLore(
        Component.text("It's Leviosa, not Leviosa!")
    )
    .build();
```

> **Note** `setCustomModelData` uses the legacy integer system, compatible with 1.21.1+
> If you're on 1.21.3+ and need the new component syhstem, use Paper's
> `setCustomModelDataComponent` directly via `editMeta`.

## Available Methods

| Method | Description |
|--------|-------------|
| `setDisplayName(Component)` | Sets the item's display name |
| `setAmount(int)` | Sets the stack size |
| `addLore(Component...)` | Adds one or more lines of lore |
| `setCustomModelData(int)` | Sets custom model data |
| `addItemFlags(ItemFlag...)` | Adds one or more item flags |
| `setUnbreakable(boolean)` | Sets whether the item is unbreakable |
| `setDamage(int)` | Sets the item's damage value |
| `enchant(Enchantment, int)` | Adds an enchantment with a level |
| `build()` | Builds and returns the final `ItemStack` |
