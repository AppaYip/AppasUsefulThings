# AppasUsefulThings

A Paper plugin library providing utilities for GUI management, spell systems, and particle shapes.

> This project is in early development. APIs may change.

## Requirements

- Java 21+
- Paper 1.21+

## Installation

### Add as a depency (Maven)

```xml
    org.appa
    appaUsefulThings
    VERSION
```

### Features

- **Logger** - A simple wrapper for paper's logger
- **GuiManager** - Interface-based GUI system with automatic asession management
- **ItemBuilder** - An Item builder designed to make ItemStack editing easier

### Logger

```java
new Logger(this).log("Hi :3");
```

See [Logger Docs](docs/logger.md) for more information.

### GuiManager

```java
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
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}

// Register and open
guiManager.registerGui(new ExampleGui());
guiManager.open(player, "example");
```

See [GUI Manager Dosc](docs/gui-manager.md) for more information.

### Item Builder

```java
ItemStack item = new ItemBuilder(Material.FIREFLY_BUSH)
    .setDisplayName(Component.text("You would not believe your eyes..."))
    .build();
```

See [ItemBuilder docs](docs/item-builder.md) for more information.

## Documentation

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)
- [ItemBuilder](docs/item-builder.md)

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
