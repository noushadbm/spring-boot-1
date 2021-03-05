package com.rayshan.gitinfo.bdd.utils;

public class TimeUtil {

    private final int value;
    private final TimeUnits unit;

    public TimeUtil(int value, TimeUnits unit) {
        this.value = value;
        this.unit = unit;
    }

    public static TimeUtil of(int value, String unit) {
        return new TimeUtil(value, TimeUnits.of(unit));
    }

    public int getValue() {
        return value;
    }

    public TimeUnits getUnitDict() {
        return unit;
    }
}
