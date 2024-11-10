package com.soitio.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum BodyStyle {

    STATION_WAGON("Station Wagon"),
    SUV("SUV"),
    COUPE("Coupe"),
    HATCHBACK("Hatchback"),
    PICKUP("Pickup"),
    SEDAN("Sedan");

    private final String name;
    private static final Map<String, BodyStyle> names = new HashMap<>();

    BodyStyle(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static BodyStyle getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (BodyStyle style : values()) {
            names.put(style.name, style);
        }
    }

}
