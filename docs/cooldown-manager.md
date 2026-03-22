# CooldownManager
A simple per-entity cooldown manager. Cooldowns are stored in milliseconds.
It is recommended to use Java's [TimeUnit](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html)
for easy conversions.

## Usage
Each `CooldownManager` instance tracks its own set of cooldowns independently,
so you can have separate managers for different abilities or systems.

```java
CooldownManager cooldownManager = new CooldownManager();

if (!cooldownManager.isOver(player)) {
    player.sendMessage(Component.text("Please wait " 
        + cooldownManager.getRemainingSeconds(player) 
        + " more seconds!"));
    return;
}

cooldownManager.setCooldown(player, TimeUnit.SECONDS.toMillis(5));
// do something
```

## Available Methods
| Method | Description |
|--------|-------------|
| `setCooldown(Entity, long)` | Sets a cooldown in milliseconds |
| `clearCooldown(Entity)` | Clears an entity's cooldown |
| `isOver(Entity)` | Returns true if the cooldown has expired or was never set |
| `getRemainingMillis(Entity)` | Returns the remaining cooldown in milliseconds |
| `getRemainingSeconds(Entity)` | Returns the remaining cooldown as a formatted string e.g. `5s` |
| `ticksToMillis(long)` | Converts ticks to milliseconds (1 tick = 50ms) |