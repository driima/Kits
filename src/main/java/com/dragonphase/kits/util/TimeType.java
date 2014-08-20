package com.dragonphase.kits.util;

import java.util.Map;

import com.google.common.collect.Maps;

public enum TimeType {
    YEARS("y"),
    MONTHS("mo"),
    DAYS("d"),
    HOURS("h"),
    MINUTES("m"),
    SECONDS("s"),
    MILLISECONDS("ms");
    
    private final String name;
    
    private TimeType(String name) {
        this.name = name;
    }
    
    private static final Map<String, TimeType> BY_NAME = Maps.newHashMap();
    
    public static TimeType match(String name) {
        return BY_NAME.get(name);
    }
    
    static {
        for (TimeType type : TimeType.values()) {
            BY_NAME.put(type.name, type);
        }
    }
}
