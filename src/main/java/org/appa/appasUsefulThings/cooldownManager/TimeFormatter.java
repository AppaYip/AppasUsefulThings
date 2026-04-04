package org.appa.appasUsefulThings.cooldownManager;

import java.time.Duration;

public class TimeFormatter {

    /**
     * Holds the singular and plural display strings for hours, minutes, and seconds
     * @param hourSingular      label for exactly 1 hour
     * @param hourPlural        label for any other count
     * @param minuteSingular    label for exactly 1 minute
     * @param minutePlural      label for any other count
     * @param secondSingular    label for exactly 1 second
     * @param secondPlural      label for any other count
     */
    public record Labels(
        String hourSingular, String hourPlural,
        String minuteSingular, String minutePlural,
        String secondSingular, String secondPlural
    ) {}

    /**
     * Built-in formatting presets
     */
    public enum Format {
        LONG(new Labels(" hour", " hours", " minute", " minutes", " second", " seconds"), ", "),
        SHORT(new Labels("h", "h", "m", "m", "s", "s"), " ");

        private final Labels labels;
        private final String separator;

        Format(Labels labels, String separator) {
            this.labels = labels;
            this.separator = separator;
        }
    }

    /**
     * Formats a duration using a builtin preset
     * @param millis the duration in milliseconds
     * @param format the preset to use
     * @return pretty string. "6 seconds, 2 minutes, 1 second"
     */
    public static String format(long millis, Format format) {
        return format(millis, format.labels, format.separator);
    }

    /**
     * Formats duration using custom Label and a seperator string
     * @param millis the duration in milliseconds
     * @param labels the Label to use for unit strings
     * @param separator the string placed inbetween each unit.
     * @return pretty string. ex: "6 seconds, 2 minutes, 1 second"
     */
    public static String format(long millis, Labels labels, String separator) {
        if (millis < 0) {
            millis = 0;
        }

        Duration duration = Duration.ofMillis(millis);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        StringBuilder sb = new StringBuilder();

        appendUnit(sb, hours, labels.hourSingular(), labels.hourPlural(), separator);
        appendUnit(sb, minutes, labels.minuteSingular(), labels.minutePlural(), separator);

        if(seconds > 0 || sb.isEmpty()) {
            if (!sb.isEmpty()) sb.append(separator);
            sb.append(seconds).append(seconds == 1 ? labels.secondSingular() : labels.secondPlural());
        }

        return sb.toString();
    }

    /**
     * Appends singular time unit to builder if value is greater than zero.
     * @param sb The string builder
     * @param value The value of the unit
     * @param singular the label to use when value == 1
     * @param plural the label to use when value != 1
     * @param separator the separator between appends if builder is non-empty
     */
    private static void appendUnit(StringBuilder sb, long value, String singular, String plural, String separator) {
        if (value > 0) {
            if (!sb.isEmpty()) {sb.append(separator);}
            sb.append(value).append(value == 1 ? singular : plural);
        }
    }
}
