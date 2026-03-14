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

- **GuiManager** - Interface-based GUI system with automatic asession management

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

## Documentation

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
