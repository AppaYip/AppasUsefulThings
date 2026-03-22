# AppasUsefulThings

A Paper plugin library providing utilities for GUI management, spell systems, and particle shapes.
All "library" methods are java doced, if you need more information please look at them or open an issue.

> This project is in early development. APIs may change.
> **Dev Builds** are built from the latest commit to the dev branch. If you're using a dev build, be aware it may contain untested or incomplete code
> It is **highly** recommended that you use the latest on the main branch.

## Requirements

- Java 21+
- Paper 1.21+

## Installation

### Adding as a dependcy

I highly suggst you use jitpack. [My jitpack url](https://jitpack.io/#AppaYip/AppasUsefulThings)

### Features

- **Logger** - A simple wrapper for paper's logger
- **GuiManager** - Interface-based GUI system with automatic asession management
- **ItemBuilder** - An Item builder designed to make ItemStack editing easier
- **CooldownManager** A cooldown manager desinged to make managing cooldowns easy

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

```java
CooldownManager cooldownManager = new CooldownManager(); // Get an instance
cooldownManager.setCooldown(player, TimeUnit.SECONDS.toMillis(5)); // Set cooldown to 5 seconds
if (!(cooldownManager.isOver(player))) {
    player.sendMessage("The cooldown is not over!");
}
```

See [CooldownManager](docs/cooldown-manager.md) for more information.

## Documentation

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)
- [ItemBuilder](docs/item-builder.md)
- [CooldownManager](docs/cooldown-manager.md)

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
