# GuiManager

An interface-based GUI system that handles inventory event routing per player automatically.

## Setup

`GuiManager` is created and registered by `AppasUsefulThings.builder()`. You should **not** create
your own instance separately, as events will not route correctly if you do.

```java
private AppasUsefulThings aut;

@Override
public void onEnable() {
    aut = AppasUsefulThings.builder()
        .enableGuiManager()
        .build(this);
}

public GuiManager getGuiManager() {
    return aut.getGuiManager();
}
```

## Implementing a GUI

### Basic GUI

Use `Gui` when you want to display an inventory with no built-in event handling.
You will need to write your own event handlers — click events are **not** automatically cancelled.

```java
public class ExampleGui implements Gui {
    private final Inventory inventory = Bukkit.createInventory(null, 9, Component.text("Example"));

    @Override
    public String getId() { return "example"; }

    @Override
    public Inventory getInventory() { return inventory; }
}
```

### Interactive GUI

Use `GuiInteractions` when you need to handle clicks, opening, or closing.
All methods have default no-op implementations — only override what you need.

```java
public class ExampleGui implements GuiInteractions {
    private final Inventory inventory = Bukkit.createInventory(null, 9, Component.text("Example"));

    @Override
    public String getId() { return "example"; }

    @Override
    public Inventory getInventory() { return inventory; }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        event.getPlayer().sendMessage(Component.text("Closed!"));
    }
}
```

## Registering

Registering a GUI allows it to be opened by ID. Events are automatically routed to the correct GUI instance.

> [!WARNING]
> Registering a GUI with an ID already in use will throw an `IllegalStateException`. GUIs will not be silently overridden.

```java
getGuiManager().registerGui(new ExampleGui());
```

## Opening

```java
// By instance
getGuiManager().open(player, gui);

// By registered ID
getGuiManager().open(player, "example");
```

## Closing

`GuiManager` handles closing automatically when `InventoryCloseEvent` fires. You can also close manually — note this does **not** close the player's inventory client-side.

```java
getGuiManager().close(player);
```

## Available Methods

| Method | Description |
|--------|-------------|
| `registerGui(Gui)` | Registers a GUI by its ID |
| `unregisterGui(Gui)` | Removes a GUI from the registry |
| `open(Player, Gui)` | Opens a GUI instance for a player |
| `open(Player, String)` | Opens a registered GUI by ID for a player |
| `close(Player)` | Removes the player's active GUI from tracking |
| `isOpen(Player)` | Returns `true` if the player currently has a GUI open |
