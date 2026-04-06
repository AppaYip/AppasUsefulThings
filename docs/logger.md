# Logger

A logger builder with the ability to do colors. 
Note this is kind of scuffed.

> [!NOTE]
> I **highly** recommend checking out the [Paper Adventure docs](https://docs.papermc.io/adventure/text/) — this is how most styling is done.

> [!NOTE]
> Colored prefixes are only supported on the `INFO` log level. `WARN` and `ERROR` use Paper's built-in
> `ComponentLogger` to ensure correct log level routing, which unfortunately forces the prefix color to
> yellow and red respectively. This is intentional to avoid a double prefix. If you know a better way,
> please open an issue or PR!

## Setup

Getting an instance. To get an instance, you must use the builder.

```java
Logger logger = Logger.builder(this)
    .build();
```

## Builder Methods

| Method | Description |
|-------|-------------|
| `setPrefix(Component prefix)` | Sets the prefix. Note this is **only** for the info log level. This will pull your plugin's prefix from its plugin.yml if it's a non-log level |
| `defaultLogLevel(LogLevel)` | The default log level (see enum below) |
| `build()` | Returns the instance |

## Log Levels

| Level | Description |
|-------|-------------|
| `INFO` | General information, white in console by default. |
| `WARN` | Something unexpected, will unfortauntely force the prefix color to be yellow |
| `ERROR` | Something went wrong, will unfortauntely force the prefix color to be red |

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

| Method | Description |
|--------|-------------|
| `log(String)` | Sends a `String` to console with the builder-defined log level. (Defaults to info if null) |
| `log(LogLevel, String)` | Sends a `String` to console with a specified log level. |
| `log(Component)` | Sends a `Component` to console with the builder-defined log level. (Defaults to info if null) |
| `log(LogLevel, Component)` | Sends a `Component` to console with a specified log level. |
| `log(String message, TextColor color)` | Sends a colored String to console. |
| `log(LogLevel logLevel, String message, TextColor color)` | Sends a colored string to console with a specified log level. (Defaults to info if null)|
