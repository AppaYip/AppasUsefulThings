package org.appa.appasUsefulThings.cooldownManager;

import java.time.Duration;

public class TimeFormatter {

    public record Labels(
        String hourSingular, String hourPlural,
        String minuteSingular, String minutePlural,
        String secondSingular, String secondPlural
    ) {}

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

    public static String format(long millis, Format format) {
        return format(millis, format.labels, format.separator);
    }

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
            appendUnit(sb, seconds, labels.secondSingular, labels.secondPlural, separator);
        }

        return sb.toString();
    }


    private static void appendUnit(StringBuilder sb, long value, String singular, String plural, String separator) {
        if (value > 0) {
            if (!sb.isEmpty()) {sb.append(separator);}
            sb.append(value).append(value == 1 ? singular : plural);
        }
    }
}
