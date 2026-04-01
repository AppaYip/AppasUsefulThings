# Logger

A colored console logger built on Adventure components.

> [!NOTE]
> I **highly** recommend checking out the [Paper Adventure docs](https://docs.papermc.io/adventure/text/) — this is how most styling is done.

> [!NOTE]
> Colored prefixes are only supported on the `INFO` log level. `WARN` and `ERROR` use Paper's built-in
> `ComponentLogger` to ensure correct log level routing, which unfortunately forces the prefix color to
> yellow and red respectively. This is intentional to avoid a double prefix. If you know a better way,
> please open an issue or PR!

## Setup

```java
Logger logger = Logger.builder(this)
    .build();
```

## Builder Methods

| Method | Description |
|--------|-------------|
| `builder(JavaPlugin)` | Creates a new Logger builder for the given plugin |
| `setPrefix(Component)` | Sets a colored prefix. Only applies to `INFO` level — see note above |
| `defaultLogLevel(LogLevel)` | Sets the default log level used when none is specified. Defaults to `INFO` |
| `build()` | Returns the configured `Logger` instance |

## Log Levels

| Level | Description |
|-------|-------------|
| `INFO` | General information. Supports colored prefix via `Bukkit.getConsoleSender()` |
| `WARN` | Something unexpected. Prefix color forced to yellow by Paper's logger |
| `ERROR` | Something went wrong. Prefix color forced to red by Paper's logger |

## Logging Methods

| Method | Description |
|--------|-------------|
| `log(String)` | Logs a string at the default log level |
| `log(LogLevel, String)` | Logs a string at the specified log level |
| `log(Component)` | Logs a component at the default log level |
| `log(LogLevel, Component)` | Logs a component at the specified log level |

## Example Usage

```java
Component prefix = Component.text("[", NamedTextColor.WHITE)
    .append(Component.text("MyPlugin", NamedTextColor.GOLD))
    .append(Component.text("]", NamedTextColor.WHITE));

Logger logger = Logger.builder(this)
    .setPrefix(prefix)
    .defaultLogLevel(LogLevel.INFO)
    .build();

// String shorthand
logger.log("Plugin has started.");

// Colored component
logger.log(Component.text("Something happened!").color(NamedTextColor.GREEN));

// Specific log level
logger.log(LogLevel.WARN, "Something looks off.");
logger.log(LogLevel.ERROR, Component.text("Something went wrong.").color(NamedTextColor.RED));
```
