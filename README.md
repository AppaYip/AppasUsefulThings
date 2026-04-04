# AppasUsefulThings

A utility library for Paper plugins providing common tools like GUI management, cooldowns, and logging

> [!WARNING]
> This project is in early development. APIs may change.

> [!NOTE]
> Dev builds are built from the latest commit to the `dev` branch and may contain untested or incomplete code. It is strongly recommended that you use the latest release on `main`.

> [!NOTE]
> This is the shadeable version of the library. You do **not** need to put this in your plugins folder.

## Installation

Add JitPack to your repositories:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.AppaYip:AppasUsefulThings:v2.0.1'
}
```

Apply the Shadow plugin and relocate to avoid conflicts with other plugins using this library:

```gradle
plugins {
    id 'io.github.goooler.shadow' version '8.1.7'
}

Note, id `io.github.goooler.shadow` is a fork of `com.github.johnrengelman.shadow` that works with
java 21+.

shadowJar {
    relocate 'org.appa', 'your.plugin.package.appa'
}
```

> [!NOTE]
> `io.github.goooler.shadow` is a community fork of `com.github.johnrengelman.shadow` that supports Java 21+.

## Initialization

`build()` returns the `AppasUsefulThings` instance. Hold onto it if you need access to things like `GuiManager`.

```java
private AppasUsefulThings aut;

@Override
public void onEnable() {
    aut = AppasUsefulThings.builder()
        .enableGuiManager()      // (Optional) Enable event listening for GuiManager
        .enableBuildLogging()    // (Optional) Log messages when the instance is built
        .build(this);            // Build — `this` is your plugin instance
}
```

## Configuration

| Option | Default | Description |
|--------|---------|-------------|
| `enableGuiManager()` | `false` | Creates and registers a `GuiManager` instance for your plugin |
| `enableBuildLogging()` | `false` | Logs a message to console when the instance is built |

## Methods

| Option | Default Value | Description |
|--------|---------------|---------------------------------------------------|
| `enableBuildLogging()` | false | Logging messages upon instance being built |
| `enableGuiManager`     | false | Registers GuiManager events for your plugin |

## Features

* Logger -- A logger builder with the ability to do colors
* GuiManager -- Interface-based GUI system with automatic session management
* ItemBuilder -- Makes building and editing ItemStacks easier
* CooldownManager -- Makes managing per-player cooldowns easy
* TimeFormatter -- Makes formatting milliseconds instaed easy to read text easy.

### Documentation

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)
- [ItemBuilder](docs/item-builder.md)
- [CooldownManager](docs/cooldown-manager.md)
- [TimeFormatter](docs/time-formatter.md)

## Requirements

- Paper 1.21.8+
- Java 21+

> [!WARNING]
> May work on older versions, however it is untested.

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
