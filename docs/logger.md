# Logger

A logger builder with the ability to do colors. 
Note this is kind of scuffed.

> [!NOTE]
> I **highly** recommend you check out [Paper Component docs](https://docs.papermc.io/adventure/text/) as this is how most styling will be done.

> [!NOTE]
> If you **do not** use the log level info, it will pull your prefix from your plugin.yml and it will **not** be colored.
> This is an unfortunate limitation I found by only being able to do prefix color using `Bukkit.getConsoleSender().sendMessage()`
> If someone knows a better way, please open an issue or PR and let me know

## Setup

Getting an instance. To get an instance, you must use the builder.

```java
Logger logger = Logger.builder(this)
    .build();
```

## Builder Methods

All components can be colored.

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
private Component prefix = Component.text("[", NamedTextColor.WHITE)
        .append(Component.text("AUT", NamedTextColor.GOLD))
        .append(Component.text("]", NamedTextColor.WHITE));

Logger infoLogger = Logger.builder(this)
    .setPrefix(prefix)
    .build();

infoLogger.log("This will default to info.");
infoLogger.log(Component.text("This is a text component").color(NamedTextColor.GREEN));

infoLogger.log(LogLevel.WARN, "This is a warning");
infoLogger.log(LogLevel.WARN, Component.text("You can also do text Components").color(NamedTextColor.YELLOW));
```


## Logging Methods

| Method | Description |
|--------|-------------|
| `log(String)` | Sends a `String` to console with the builder-defined log level. (Defaults to info if null) |
| `log(LogLevel, String)` | Sends a `String` to console with a specified log level. |
| `log(Component)` | Sends a `Component` to console with the builder-defined log level. (Defaults to info if null) |
| `log(LogLevel, Component)` | Sends a `Component` to console with a specified defined log level. |
