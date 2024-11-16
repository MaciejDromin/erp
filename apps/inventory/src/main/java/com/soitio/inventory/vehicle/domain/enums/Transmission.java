package com.soitio.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Transmission {

    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String name;
    private static final Map<String, Transmission> names = new HashMap<>();

    Transmission(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static Transmission getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (Transmission style : values()) {
            names.put(style.name, style);
        }
    }

}
