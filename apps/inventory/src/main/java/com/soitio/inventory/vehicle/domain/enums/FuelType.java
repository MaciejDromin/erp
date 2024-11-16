package com.soitio.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum FuelType {

    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    LPG("LPG"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric"),
    HYDROGEN("Hydrogen");

    private final String name;
    private static final Map<String, FuelType> names = new HashMap<>();

    FuelType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static FuelType getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (FuelType style : values()) {
            names.put(style.name, style);
        }
    }

}
