# GuiManager

An interface-based GUI system that handles inventory event routing per player automatically.

## Setup

To get an instance of GuiManager, use `AppasUsefulThings.getGuiManager()` after registering.
If you make your own instance, then you have to register the class as a Bukkit event listener yourself. 
Using `getGuiManager();` does this for you.
Calling this method return the same instance every call.

```java
@Override
public void onEnable() {
    AppasUsefulThings.initialize(this);
    GuiManger guiManager = AppasUsefulThings.getGuiManager();
}
```

## Implementing a GUI

### Basic GUI

Use `Gui` when you want to display an inventory with no built-in event handling.
Inventory names are ignored. This is due to Paper not providing a way to get an inventory's name without a player viewing it.
It is recommended you provide an empty component due to the string method being deprecated.

```java
public class ExampleGui implements Gui {
    private final Inventory inventory = Bukkit.createInventory(null, 9, Component.empty());

    @Override
    public String getId() { return "example"; }

    @Override
    public Inventory getInventory() { return inventory; }
    
    @Override // Defaulted method. This will return an empty component. You do not need to override if you do not want a title.
    public Component getTitle() {
        return Commponent.text("Example");
    }
}
```

### Interactive GUI

Use `InteractiveGui` when you need to handle clicks, opening, or closing.
All methods have default implementations that do nothing.

```java
public class ExampleGui implements GuiInteractions {
    private final Inventory inventory = Bukkit.createInventory(null, 9, Component.empty());

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

Registering a GUI allows it to be opened by ID or optionally an instance of it. 
Events are automatically routed to the correct GUI instance.

> [!WARNING]
> Registering a GUI with an ID already in use will throw an `IllegalStateException`. 
> GUIs will not be silently overridden unless you provide true to registerGui(Gui, boolean)

```java
AppasUsefulThings.getGuiManager().registerGui(new ExampleGui());
AppasUsefulThings.getGuiManager().registerGui(new ExampleGui(), true); // Overrides gui
```

## Opening

```java
// By instance
AppasUsefulThings.getGuiManager().open(player, gui);

// By registered ID
AppasUsefulThings.getGuiManager().open(player, "example");
```

## Closing

`GuiManager` handles closing automatically when `InventoryCloseEvent` fires. You can also close manually — note this does **not** close the player's inventory client-side.

```java
AppasUsefulThings.getGuiManager().close(player);
```

## Available Methods

| Method                      | Description                                                      |
|-----------------------------|------------------------------------------------------------------|
| `registerGui(Gui)`          | Registers a GUI by its ID                                        |
| `registerGui(Gui, boolean)` | Registers a GUI by its ID, optionally overwrite an existing gui. |
| `unregisterGui(Gui)`        | Removes a GUI from the registry                                  |
| `open(Player, Gui)`         | Opens a GUI instance for a player                                |
| `open(Player, String)`      | Opens a registered GUI by ID for a player                        |
| `close(Player)`             | Removes the player's active GUI from tracking                    |
| `isOpen(Player)`            | Returns `true` if the player currently has a GUI open            |
