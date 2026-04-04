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

### Formatting Remaining Time

You can use [TimeFormatter](time-formatter.md) to display the remaining cooldown
in easier to read format instead of raw millisecond count.

```java
CooldownManager cooldownManager = new CooldownManager();

if (!cooldownManager.isOver(player)) {
    long remaining = cooldownManager.getRemainingMillis(player);
    String formatted = TimeFormatter.format(remaining, TimeFormatter.Format.LONG);

    player.sendMessage(Component.text("Please wait " + formatted + " before using this again!"));
    return;
}

cooldownManager.setCooldown(player, TimeUnit.MINUTES.toMillis(2));
// do something
```

## Available Methods
| Method | Description |
|--------|-------------|
| `setCooldown(Entity, long)` | Sets a cooldown in milliseconds |
| `clearCooldown(Entity)` | Clears an entity's cooldown |
| `isOver(Entity)` | Returns true if the cooldown has expired or was never set |
| `getRemainingMillis(Entity)` | Returns the remaining cooldown in milliseconds |
| `getRemainingSeconds(Entity)` | Returns the remaining cooldown in seconds as a string e.g. `5` |
| `ticksToMillis(long)` | Converts ticks to milliseconds (1 tick = 50ms) |
