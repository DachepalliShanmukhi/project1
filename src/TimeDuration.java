package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration extends Token {
    private static final Pattern PATTERN = Pattern.compile("(?i)(\\d+(?:\\.\\d+)?)(ms|s|m|h)");
    private final long milliseconds;

    public TimeDuration(String value) {
        super(value);
        Matcher matcher = PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time duration format: " + value);
        }

        double number = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(2).toLowerCase();

        switch (unit) {
            case "ms": this.milliseconds = (long) number; break;
            case "s": this.milliseconds = (long) (number * 1000); break;
            case "m": this.milliseconds = (long) (number * 60 * 1000); break;
            case "h": this.milliseconds = (long) (number * 60 * 60 * 1000); break;
            default: throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    public long getMilliseconds() { return milliseconds; }
}