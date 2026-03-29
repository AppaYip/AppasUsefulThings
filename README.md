# AppasUsefulThings

A Paper plugin library providing utilities for GUI management, spell systems, and particle shapes.
All "library" methods are Javadoc'd. If you need more information, please check them or open an issue.

> [!WARNING]
> This project is in early development. APIs may change.

> [!NOTE]
> Dev builds are built from the latest commit to the `dev` branch and may contain untested or incomplete code. It is strongly recommended that you use the latest release on `main`.

## Requirements

- Java 21+
- Paper 1.21+

## Features

- **Logger** — A simple wrapper for Paper's logger
- **GuiManager** — Interface-based GUI system with automatic session management
- **ItemBuilder** — Makes building and editing `ItemStack`s easier
- **CooldownManager** — Makes managing per-player cooldowns easy

## Installation

### Adding as a dependency

It's recommended to use JitPack. [View on JitPack](https://jitpack.io/#AppaYip/AppasUsefulThings)

In your `plugin.yml`, add AppasUsefulThings as a dependency:
```yml
depend:
  - AppasUsefulThings
```

Or as a soft dependency if it's optional:
```yml
softdepend:
  - AppasUsefulThings
```

## Usage

### Logger
```java
new Logger(this).log("Hi :3");
```

See [Logger docs](docs/logger.md) for more information.

### GuiManager
```java
public class ExampleGui implements GuiInteractions {

    final TextComponent title = Component.text("Example GUI")
        .color(NamedTextColor.LIGHT_PURPLE);

    private final Inventory inventory = Bukkit.createInventory(
        null,
        9 * 3,
        title
    );

    public ExampleGui() {
        ItemStack border = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, border);
            inventory.setItem(i + 18, border);
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
guiManager.open(player, "ExampleGui");
```

See [GuiManager docs](docs/gui-manager.md) for more information.

### ItemBuilder
```java
ItemStack item = new ItemBuilder(Material.FIREFLY_BUSH)
    .setDisplayName(Component.text("You would not believe your eyes..."))
    .build();
```

See [ItemBuilder docs](docs/item-builder.md) for more information.

### CooldownManager
```java
CooldownManager cooldownManager = new CooldownManager();
cooldownManager.setCooldown(player, TimeUnit.SECONDS.toMillis(5)); // 5 seconds
if (!cooldownManager.isOver(player)) {
    player.sendMessage("The cooldown is not over!");
}
```

See [CooldownManager docs](docs/cooldown-manager.md) for more information.

## Documentation

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)
- [ItemBuilder](docs/item-builder.md)
- [CooldownManager](docs/cooldown-manager.md)

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
