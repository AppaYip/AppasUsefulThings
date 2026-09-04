# Item Builder

This is a builder designed to remove some of the headache when trying to make items.

All methods return an instance of themselves, thus allowing for fluid/builder api.
Some methods may be broken up into nested consumers.


## Creating an item

Items are created through the static `ItemBuilder.of(...)` method:

```java
ItemBuilder.of(Material.STICK, item -> item
        .name("Wand")
        .lore("You", "Shall not", "Pass"); // Each string is a new line. Can optionally use Components.
);
```

The first argument is the `Material` to use, the second argument is a `Consumer<Item>` used to configure the item.

`Material` must be a valid item material. Attempting to use a non-item material, an IllegalArgumentException will be thrown.

## Basic Properties

Basic item properties are configured directly on the `Item` builder.

| Method             | What it does                                    |
|--------------------|-------------------------------------------------|
| name(String)       | Chagnes the item's name                         |
| name(Component)    | Changes the item's name                         |
| amount(integer)    | Sets the amount of item's. This defaults to one |
| lore(String...)    | Adds one or more Strings to the item's lore     |
| lore(Component...) | Adds one or more Components to the item's lore  |

## Appearance

Appearance related item properties.

```java
ItemBuilder.of(Material.STICK, item -> item
        .appearance(a -> a
            .flags(ItemFlags.HIDE_ENCHANTS)
    )
);
```

| Method              | What it does                 |
|---------------------|------------------------------|
| flags(ItemFlags...) | Adds one or more item flags. |
| modelData()         | Gets the meta data Object    |


```java
Itemuilder.of(Material.STICK, item -> item
        .appearance(a -> a
            .modelData(d -> d 
                d.strings("Wand")
        )
    )
);
```

| Method           | What it does                                        |
|------------------|-----------------------------------------------------|
| floats(float...) | Adds one or more floats to the custom model data.   |
| flags(...)       | Adds one or more booleans to the custom model data. |
| strings(...)     | Adds one or more strings to the custom model data.  |
| colors(...)      | Adds one or more colors the custom model data.      |




### Enchanted item

```java
ItemStack item = ItemBuilder.of(Material.SADDKLE, item -> item
        .name("Wand")
            .lore("", "")

            .enchantments(e -> e
                .add(Enchantment.UNBREAKING, 3)
                .add(Enchantment.SHARPNESS, 5)
            )

            .appearance(a -> a
                .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_STORED_ENCHANTS)
            )
);
```


### Other Properties

Several other properties can be configured. These can be used directly with the item configuration.

| Method        | What it does                          |
|---------------|---------------------------------------|
| unbreakable() | Makes the item unbreakable.           |
| damage(int)   | Adds damage to the item's durability. |
| hideToolTip() | Hides the item's tooltip.             |
