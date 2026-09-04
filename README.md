# AppasUsefulThings

**AppasUsefulThings** is a utility library for Paper plugins that provides reusuable tools for common tasks.

> [!WARNING]
> This project is in early development. APIs may change.

> [!NOTE]
> The `main` branch and may contain untested or incomplete code. It is strongly recommended that you use the latest release.

> [!NOTE]
> This is the shadeable version of the library. You do **not** need to put this in your plugin's folder.

## Installation

### 1. Add JitPack

Add JitPack to your Gradle Repositories:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

### 2. Add AppasUsefulThings

Add the library as an implementation dependency:

```gradle
dependencies {
    implementation 'com.github.AppaYip:AppasUsefulThings:v<version>'
}
```

### 3. Shade and relocate.

Because this library is designed to be shaded into your plugin, relocate its packages to avoid conflicts with other plugins using AppasUsefulThings.

For example:

```gradle
plugins {
    id("com.gradleup.shadow") version "9.4.1"
}

shadowJar {
    relocate 'org.appa', 'your.plugin.package.appa'
}
```

> [!NOTE]
> Make sure the relocation package matches the package used by the library version you are building against.

## Initialization

Before using managers that depend on a plugin instance, initialize AppasUsefulThings from your plugin's onEnable() method:

```java
@Override
public void onEnable() {
    AppasUsefulThings.initialize(this);
}
```

initialize() may only be called once. 
Calling it again after the library has already been initialized will throw an IllegalStateException.

## Managers

AppasUsefulThings exposes several managers through static accessor methods.
Managers are initialized only when first requested, so you only need to access the utilities your plugin actually uses.

### GuiManager

Use `getGuiManager()` to access the GUI system:

```java
GuiManager guiManager = AppasUsefulThings.getGuiManager();
```

The manager is created lazily the first time it is requested. 
Its event listener is automatically registered with Bukkit.

The same GuiManager instance is returned on subsequent calls.

#### Initialization required: Yes.

### Better Power Tools

Use `getBetterPowerTools()` to access the Better Power Tools functionality:

```java
BetterPowerTools powerTools = AppasUsefulThings.getBetterPowerTools();
```

The manager is created lazily and automatically handles its event registration.
The same BetterPowerTools instance is returned on subsequent calls.

#### Initialization required: Yes.

### CooldownManager

Use `getCooldownManager()` to access the cooldown system:

```java
CooldownManager cooldowns = AppasUsefulThings.getCooldownManager();
```

CooldownManager is created lazily and the same instance is returned on subsequent calls.
Unlike the other managers, CooldownManager currently does not require AppasUsefulThings.initialize() to be called first.

> [!Note]
> This behavior may change in a future version.



## Available Utilities

* **Logger** -- A logging utility with support for colored messages.
* **GuiManager** -- An interface-based GUI system with automatic session management.
* **ItemBuilder** -- Utilities for creating `ItemStack`s.
* **CooldownManager** -- Simple per-player cooldown management.
* **TimeFormatter** -- Utilities for converting milliseconds into human-readable text.
* **Better Power Tools** -- Utilities for assigning callbacks to item events.

### Documentation

More detailed documentation is available for each major component:

- [Logger](docs/logger.md)
- [GuiManager](docs/gui-manager.md)
- [ItemBuilder](docs/item-builder.md)
- [CooldownManager](docs/cooldown-manager.md)
- [TimeFormatter](docs/time-formatter.md)
- [Better Power Tools](docs/bettper-power-tools.md)

However mostly all methods will be JavaDoced.

## Requirements

- Paper 1.21.11+
- Java 21+

> [!WARNING]
> Older Paper versions may work, but they are not currently tested or fully supported.

## Example

A basic plugin using AppasUsefulThings might look like:

```java
public final class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        AppasUsefulThings.initalize(this);
        
        GuiManager guiManager = AppasUsefulThings.getGuiManager();
    }
}
```

## Contributing

Contributions, bug reports, and feature requests are welcome.

Feel free to open an issue or submit a pull request on GitHub.

## License

This project is licensed under the [MIT](LICENSE)
