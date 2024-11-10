package com.soitio.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum DriveTrain {

    FWD("Front Wheel Drive"),
    RWD("Rear Wheel Drive"),
    AWD("All Wheel Drive"),
    AWD_FWD("AWD W/FWD"),
    AWD_RWD("AWD W/RWD");

    private final String name;
    private static final Map<String, DriveTrain> names = new HashMap<>();

    DriveTrain(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static DriveTrain getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (DriveTrain style : values()) {
            names.put(style.name, style);
        }
    }

}
