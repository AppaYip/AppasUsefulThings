# TimeFormatter

## Usage

### Built-in Formats

Two presets are available out of the box via `TimeFormatter.Format`.

```java
long ms = 5461000; // 1 hour, 31 minutes, 1 second

TimeFormatter.format(ms, TimeFormatter.Format.LONG) // "1 hour, 31 minutes, 1 second"
TimeFormatter.format(ms, TimeFormatter.Format.SHORT) // ""1h 31m 1s""
```

### Custom Labels

If you want to change unit strings and or seperators, you can supply your own `Labels` instance.
Label strings are appended directly after numeric values, so include a leading space if you want one.

```java
TimeFormatter.Labels labels = new TimeFormatter.Labels(
    " hr", " hrs",
    " min", " mins",
    " sec", " secs"
);

TimeFormatter.format(ms, labels, ", ") // "1 hr, 31 mins, 1 sec"
```

### Zero-value units

Units with a value of zero are omitted automatically. Seconds are always shown
if no larger unit is present, output is never empty.

```java
TimeFormatter.format(120000, Format.LONG) // "2 minutes" -- seconds omitted
TimeFormatter.format(0, Format.LONG)      // "0 seconds" -- fallback to seconds
```

## Available Methods

| Method | Description |
|--------|-------------|
| `format(long, Format)` | Formats a duration using a built-in preset. |
| `format(long, Labels, String)` | Formats a duration using custom labels and a separator. |

## Formats

| Preset | Example output |
|--------|----------------|
| `Format.LONG` | `"2 hours, 5 minutes, 1 second"` |
| `Format.SHORT` | `"2h 5m 1s"` |
