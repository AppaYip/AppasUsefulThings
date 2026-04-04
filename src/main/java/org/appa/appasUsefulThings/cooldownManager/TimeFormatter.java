package org.appa.appasUsefulThings.cooldownManager;

public class TimeFormatter {

    public enum Format {
        LONG,   // "1 hour, 2 minutes, 3 seconds"
        SHORT,  // "1h 2m 3s"
        CUSTOM  // custom labels
    }

    private static final String[][] LABELS = {
            // Format.LONG
            {"hour", "hours", "minute", "minutes", "second", "seconds"},
            // Format.SHORT
            {"h", "h", "m", "m", "s", "s"}
    };

    
}
