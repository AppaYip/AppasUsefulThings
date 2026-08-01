# Better Power Tools

A lightweight power tool system for binding callbacks to items.

A power tool is identified by string ID stored directly on an `ItemStack` using it's `Persistent Data Container`.
When A player interacts with the item via, dropping, swapping, or clicking, the corresponding `PowerTool` callback is executed.

## Setup

Create a `BetterPowerTools` instance using your plugin instance.

```java
private BetterPowerTools betterPowerTools;

@Override
public void onEnable() {
    betterPowerTools = new BetterPowerTools(this);
}
```

`BetterPowerTools` automatically registers the required event listeners when it is constructed.

You can access the underlying managers through their getters:

```java
betterPowerTools.getPowerToolRegistry();
betterPowerTools.getItemStorage();
betterPowerTools.getPlayerSettingsManager();
```

## Registering a Power Tools

Power Tools are registered under a unique string ID>.

```java
betterPowerTools.register("lightning", context -> {
    Player player = context.player();
    
    player.getWorld().strikeLightning(player.getLocation());
    
    return false;
});
```

The callback receives a `PowerToolContext` containing information about the player, item, and event that triggered the power tool.\
The callback should return `true` if the underlying Bukkit event should be canceled.

```java
powerTools.register("example", context -> {
    // do something
    return true; /* This will cancel **all** events for this item,
                    dropping, swapping, clicking. */  
});
```

Power Tool IDs are case-insensitive and are stored in lowercase.

> [!WARNING]
> `register(String, PowerTool)` will silently replace an existing power tool with the same iD.\
> Use `PowerToolRegistery.registerIfAbsent(...)` if you want duplicate registrations to throw an exception instead.`

## Binding an Item

Once a power tool has been registered, it can be bound to an item.

```java
ItemStack wand new ItemSatck(Material.BLAZE_ROD);

betterPowerTools.bind(wand, "lightning");
```

The ID is stored on the `ItemStack` using its PDC.

The provided `ItemStack` is mutated directly.

## Clearing an Item

Remove the power tool ID from an item with:

`betterPowerTools.clear(item);`

You can also check whether an item currently has a power tool:

```java
if (betterPowerTools.hasPowerTool(item)) {
    // Item has a power tool ID        
}
```

To retrieve the ID:

```java
Optional<String> id = powerTool.getId(item);

id.ifPresent(powerToolId -> {
    // Do something with the id
});
```

## Player Settings

You can disable a player's power tools individually.

Power Tools are enable by default
```java
if (betterPowerTools.isEnabled(player)) {
    // Power tools are enabled.
}
```

### Toggle

Toggle the player's current setting:

```java
boolean enabled = betterPowerTools.toggle(player);

player.sendMessage(Component.txet(
        enabeld ? "Power Tools enabled!" : "Power Tools Disabled!"
));
```

### Set Enabled State

You can explicitly enable or disable power tools for a player

`betterPowerTools.setEnabled(player, false);`

### Clear Player Settings

The underlying `PlayerSettingsManager` can also clear the player's preference:

`betterPowerTools.getPlayerSettingsManager().clear(player);`

Clearing the preference causes the player to return to the default state, which is enabled.

## Power Tool Events

A power tool can be triggered by several different actions.

The event type is available through `PowerToolContext.type()`

| Event Type             | Description                                      |
|------------------------|--------------------------------------------------|
| `LEFT`                 | Player left-clicked with the item                |
| `RIGHT`                | Player right-clicked with the item               |
| `DROP`                 | Player attempted to drop the item                |
| `SWAP_MAIN_TO_OFFHAND` | Player swapped the main-hand item to the offhand |
| `SWAP_OFFHAND_TO_MAIN` | Player swapped the offhand item to the main hand |

You can use event type to make one power tool perform different actions:

```java
betterPowerTools.register("example", context ->{
    switch (context.type()) {
        case LEFT -> {
            // Left click
        } 
        
        case RIGHT -> {
            // Right click
        }
        
        case DROP -> {
            // Drop
        } 
        
        case SWAP_MAIN_TO_OFFHAND -> { 
            // Main hand -> offhand 
        } 
        
        case SWAP_OFFHAND_TO_MAIN -> { 
            // Offhand -> main hand 
        } 
    }
    
    return false;
});
```

## PowerToolContext

`PowerToolContext` contains information about the execution.

```java
public record PowerToolContext<T>(
        Player player,
        ItemStack item,
        PowerToolEventType type,
        @Nullable T eventData
) {}
```

### Available Data

| Method        | Description                                  |
|---------------|----------------------------------------------|
| `player()`    | The player who triggered the power tool      |
| `item()`      | The item that triggered the power tool       |
| `type()`      | The event that triggered the power tool      |
| `eventData()` | Additional event-specific data, if available |

> [!NOTE]
> `eventData()` is currently `null` for the built-in events.
> It is reserved for event-specific data for the future.

## Canceling Events

A `PowerTool` returns a boolean indicating whether the underlying Bukkit event should be canceled.

```java
betterPowerTools.register("nodrop", context -> {
    if (context.type() == PowerToolEventType.DROP) {
        context.player().sendMessage(Component.text("You cannot drop this!"));
        return true;
    }
    
    return false;
});
```

## Complete Example

A very basic lightning wand:

```java
public class MyPlugin extends JavaPlugin {
    private BetterPowerTools betterPowerTools;
    
    @Override
    public void onEnable() {
        betterPowerTools = new BetterPowerTools(this);
        
        betterPowerTools.register("lightning", context -> {
            if (context.type() != PowerToolEventType.RIGHT) return false;
            
            Player player = context.player();
            player.getWorld().strikeLightning(
                    player.getTargetBlockExact(50).getLocation()
            );
            
            return true;
        });
    }
}
```

The power tool can then be attached to an item:

```java
ItemStack wand = new ItemStack(Material.BLAZE_ROD);

betterPowerTools.bind(wand, "lightning");
```

Any player holding that item can now trigger the registered power tool.

## Optional Command

Better Power Tools includes an optional `/betterpowertools` command for testing.

The command is **not enabled by default, and currently doesn't check if another plugin has already registered it.** (This may change)

Enable it when constructing your plugin:

```java
@Override
public void onEnable() {
    betterPowerTools = new BetterPowerTools(this);
    
    betterPowerTools.enableCommand();
}
```

The current command has the following:

| Argument                    | Description                                  |
|-----------------------------|----------------------------------------------|
| `/betterpowertools set <id> | Binds the held item to a power tool ID       |
| `/betterpowertools get`     | Displays the power tool ID on the held item  |
| `/betterpowertools clear`   | Removes the power tool ID from the held item |
| `/betterpowertools toggle`  | Toggles the player's power tool setting      |

Power tool IDs are suggested automatically for the set command.

## Available Methods

| Method                                               | Description                                                         |
|------------------------------------------------------|---------------------------------------------------------------------|
| `BetterPowerTools(JavaPlugin)`                       | Creates and initializes Better Power Tools                          |
| `register(String, PowerTool)`                        | Registers a power tool, replacing an existing tool with the same ID |
| `unregister(String)`                                 | Removes a registered power tool                                     |
| `get(String)`                                        | Gets a registered power tool                                        |
| `exists(String)`                                     | Returns whether a power tool is registered                          |
| `bind(ItemStack, String)`                            | Stores a power tool ID on an item                                   |
| `clear(ItemStack)`                                   | Removes a power tool ID from an item                                |
| `getId(ItemStack)`                                   | Gets the power tool ID from an item                                 |
| `hasPowerTool(ItemStack)`                            | Returns whether an item has a power tool ID                         |
| `isEnabled(Player)                                   | Returns whether power tools are enabled for a player                |
| `toggle(Player)`                                     | Toggles power tools for a player                                    |
| `setEnabled(Player, boolean)`                        | Enables or disables power tools for a player                        |
| `dispatch(Player, ItemStack, PowerToolEventType, T)` | Executes a matching power tool                                      |
