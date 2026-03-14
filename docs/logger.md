# Logger

A simple wrapper around Paper's built in logger. 
These docs are mainly here for completeness — this is primarily for internal use.

## Setup

Create an instance by providing your plugin. Optionally provide a default log level,
which will be used when calling `log(String)` without specifying a level.

```java
Logger logger = new Logger(this);                      // defaults to INFO
Logger logger = new Logger(this, Logger.LogLevel.WARN); // defaults to WARN
```

## Usage

```java
logger.log("Hello!");                             // uses the instance's default level
logger.log(Logger.LogLevel.ERROR, "Oh no!");      // overrides the default level
```

## Log Levels

| Level | Description |
|-------|-------------|
| `INFO` | General information, white in console |
| `WARN` | Something unexpected, yellow in console |
| `ERROR` | Something went wrong, red in console |

## Available Methods

| Method | Description |
|--------|-------------|
| `Logger(JavaPlugin)` | Creates a logger instance defaulting to INFO |
| `Logger(JavaPlugin, LogLevel)` | Creates a logger instance with a default log level |
| `log(String)` | Logs a message using the instance's default level |
| `log(LogLevel, String)` | Logs a message with a specific log level |
