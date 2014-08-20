package com.dragonphase.kits.util;

import java.util.Map;

import com.google.common.collect.Maps;

public enum FlagType {
    OVERWRITE,
    ANNOUNCE,
    DELAY,
    CLEAR,
    ;
    
    private static final Map<String, FlagType> BY_NAME = Maps.newHashMap();
    
    public static FlagType match(String name) {
        return BY_NAME.get(name.toLowerCase());
    }
    
    static {
        for (FlagType type : FlagType.values()) {
            BY_NAME.put(type.name().toLowerCase(), type);
        }
    }
}
