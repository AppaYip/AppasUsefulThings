# GuiManager

This is a GUI manager designed to remove some redundancy of creating guis. It also provides a helpful way to manage which player has what open.

## Setup

Getting an instance. You will want to make a getter for this instance and pass it around

```java
public static GuiManager guiManager;
public static GuiManager getGuiManager() {
    return guiManager;
}
```

And then in your on enable event,

```java
guiManager = new GuiManager();
```

### Basic Gui (No automatic event handling)

Use `Gui` when you want to display an inventory with no pre-written handling.
You will have to write **your own** event handlers, click events are **not** automatically caneled.

```java
public class ExampleGui implements Gui {
    @Override
    public String getId() { return "example"; }

    @Override
    public Inventory getInventory() { ... }
}
```

### Interactive GUI

Use `GuiInteractions` when you need to handle clicks, opening, or closing.
All methods in the GuiInteractions interface are optional, they have default implementations that do nothing.

```java
public class ExampleGui implements GuiInteractions {
    @Override
    public String getId() { return "example"; }

    @Override
    public Inventory getInventory() { ... }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
```

## Registering

Gui registering allows you to open the gui, see below.
Opening guis this way allows the GuiManager to direct events to each specific gui.
Registering a GUI with the same ID as another in the same instance will throw an IllegalStateException. GUIs will **not** be overriden.

```java
guiManager.registerGui(new ExampleGui());
```

## Opening

```java
// By instance
guiManager.open(player, gui);

// By id
guiManager.open(player, "example");
```

## Closing

The GUI manager already handles this.
When an onInventoryClose event is fired, the player will have its current GUI closed.
However, if you want to do this manually. Please note this will **not** close the player's inventory automatically.

```java
guiManager.close(player);
```

## Available Methods

| Method | Description |
|--------|-------------|
| `registerGui(Gui)` | Registers a GUI |
| `unregisterGui(Gui)` | Removes a GUI from registry |
| `open(Player, Gui)` | Opens a GUI for a player |
| `open(Player, String)` | Open a GUI by id |
| `close(Player)` | Closes the player's open GUI |
| `isOpen(Player)` | Returns whether the player has a GUI open |
