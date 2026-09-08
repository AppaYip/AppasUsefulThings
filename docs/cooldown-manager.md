# CooldownManager

`CooldownManager` provides a simple way to manage named. pre-entity cooldowns. Cooldowns **are not** persistent across restarts.

Each `CooldownManager` instance maintains its own cooldown data. 
A manager can contain multiple named cooldowns, and each track its state separately for each entity.

Cooldown timing is based on `System.currentTimeMillis()`. This means cooldowns are measured using real elapsed time
and are **not affected by server TPS or tick lag**.

## Getting the CooldownManager

You can access the shared cooldown manager through `AppasUsefulThings`:

```java
CooldownManager cooldownManager = AppasUsefulThings.getCooldownManager();
```

You can also create your own manager instance:

```java
CooldownManager cooldownManager = new CooldownManager();
```

Cooldowns are stored in one manager instance are separate from other cooldowns stored in another.

___

## Creating a Cooldown

Cooldowns are identified by a unique string ID.

```java
CooldownManager cooldownManager = AppasUsefulThings.getCooldownManager();

Cooldown fireballCooldown = cooldownManager.cooldown("fireball");
```

Calling `cooldown()` multiple times with the same ID returns the same `cooldown` handle:

```java
Cooldown first = cooldownManager.cooldown("fireball");
Cooldown second = cooldownManager.cooldown("fireball");
```

Both `first` and `second` represent the same named cooldown.
Cooldown IDs cannot be blank.

___

## Setting a Cooldown

A cooldown can be set using milliseconds:

```java
fireballCooldown.setCooldown(player, 5000);
```

The example above sets a cooldown for 5,000 milliseconds.

You can also use TimeUnit for more readable durations:


```java
fireballCooldown.setCooldown(player, 5, TimeUnit.SECONDS);
```

### Clearing with Zero

Passing a duration of `0` to the millisecond overload clear entity's cooldown:

```java
fireballCooldown.setCooldown(player, 0); // Clears the cooldown.
```

Negative durations are not allowed and will throw an `IllegalArgumentException`.

___

### Checking if a Cooldown is Active

Use `isActive()` to check if an entity currently has an active cooldown.

```java
if (fireballCooldown.isActive(player)) {
    return;
}

// The entity can use the ability.
```

Expired cooldowns **are not** removed with a scheduler. 
They **are only** removed when checked and aren't active.

## TrySet

`trySet()` attempts to set a cooldown only if the entity **does not** already have an active cooldown.

```java
boolean success = fireballCooldown.trySet(
        player,
        5,
        TimeUnit.SECONDS
);
```

If the entity already has an active cooldown, the method returns `false` and **does not** modify the existing cooldown.

A millisecond overload is also available:

```java
boolean success = fireballCooldown.trySet(player, 5000);
```

___

### Formatting Remaining Time

You can use `TimeFormatter` can convert a duration in ms into a more readable string.

```java

long remaining = fireballCooldown.getRemainingMillis(player);

String formatted = TimeFormatter.format(
        remaining, 
        TimeFormatter.Format.LONG
);
```

For example, the result may be:

`2 minutes, 6 seconds`

The `SHORT` format is also available:

```java
String formatted = TimeFormatter.format(
        remaining,
        TimeFormatter.Format.SHORT
);
```

Example output:

`2m 6s`

For custom formatting options, see the [TimeFormatter](cooldown-manager.md) documentation.

___


## Available Methods

| Method                               | Description                                                                                                |
|--------------------------------------|------------------------------------------------------------------------------------------------------------|
| `setCooldown(Entity, long)`          | Sets a cooldown in milliseconds                                                                            |
| `setCooldown(Entity, long, TimeUnit)` | Sets a cooldown using a specific time unit.                                                                |
| `trySet(Entity, long)`               | Sets a cooldown if the entity does not already have an active cooldown. Returns whether it was successful. |
| `trySet(Entity, long, TimeUnit`      | Sets a cooldown if the entity does not already have an active cooldown. Returns whether it was successful. |
| `isActive(Entity)`                   | Returns whether the entity currently has an active cooldown.                                               |
| `remaining(Entity)`                  | Returns the remaining cooldown time in milliseconds.                                                       |
| `clear(Entity)`                      | Removes the entity's cooldown.                                                                             |
| `getId(Entity)`                       | Returns the cooldown's unique ID.                                                                          |
