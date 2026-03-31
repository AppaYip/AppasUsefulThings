# AppasUsefulThings

A utiltiy library for Paper plugins providing common tools like GUI management, cooldowns, and logging

> [!WARNING]
> This project is in early development. APIs may change.

> [!NOTE]
> Dev builds are built from the latest commit to the `dev` branch and may contain untested or incomplete code. It is strongly recommended that you use the latest release on `main`.

> [!NOTE]
> This is the shadeable version of the plugin. You do **not** need to put this in your plugin folder

## Installation

Add jitpack to your repositories:

```gradle
repositories {
    maven {url 'https://jitpack.io'}
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.AppaYip:AppasUsefulThings:v2.0.0'
}
```

Then apply the Shadow Plugin and relocate to avoid conflicts with other plugins using this library:

```gradle
plugins {
    id 'com.github.johnrengelman.shadow' version '8.1.1'
}

shadowJar {
    relocate `org.appa`, `your.plugin.package.appa`
}
```

### Initialization

To use some features of AppasUsefulThings, you must get the builder and configure it.

```java
@Override
public void onEnable() {
AppasUsefulThings.builder() // Get the builder
    .enableGuiManager() // (Optionally) Enable event listening for Gui Manager
    .enableBuildLogging() // (Optionally) Enable logging messages when creating the instance.
    .build(this); // Build it, `this` is your plugin instance
}
```

### Configuration

| Option | Default Value | Description |
|--------|---------------|------------------------------------------------------|
| `enableBuildLogging()` | false | Logging messages upon instance being built   |
| `enableGuiManager`     | false | Registers GuiManager events for your pluigin |

### Features

* Logger — A logger builder with the ability to do colors
* GuiManager — Interface-based GUI system with automatic session management
* ItemBuilder — Makes building and editing ItemStacks easier
* CooldownManager — Makes managing per-player cooldowns easy

### Doccumentation

* [Logger](docs/logger.md)
* [GuiManager](docs/gui-manager.md)
* [ItemBuilder](docs/item-builder.md)
* [CooldownManager](docs/cooldown-manager.md)

### Requirements

* Paper 1.21.8+
* Java 21+

> [!WARNING]
> May work on older versions, however it is untested

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or pull request.

## License

[MIT](LICENSE)
