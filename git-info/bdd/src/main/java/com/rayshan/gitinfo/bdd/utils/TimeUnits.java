package com.rayshan.gitinfo.bdd.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public enum TimeUnits {
    MILLISECONDS(TimeUnit.MILLISECONDS, "ms", "milli", "millisecond", "milliseconds"),
    SECONDS(TimeUnit.SECONDS, "s", "sec", "second", "seconds"),
    MINUTES(TimeUnit.MINUTES, "m", "min", "minute", "minutes"),
    HOURS(TimeUnit.HOURS, "h", "hour", "hours");

    private final List<String> keys;
    private final TimeUnit timeUnit;

    TimeUnits(TimeUnit timeUnit, String... keys) {
        this.timeUnit = timeUnit;
        this.keys = Arrays.asList(keys);
    }

    public static TimeUnits of(String key) {
        for (TimeUnits timeUnits : values()) {
            if (timeUnits.keys.contains(key.toLowerCase())) return timeUnits;
        }
        throw new IllegalArgumentException("No TimeUnit found for " + key);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
