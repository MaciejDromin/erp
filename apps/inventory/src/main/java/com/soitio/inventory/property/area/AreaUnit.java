package com.soitio.inventory.property.area;

import com.fasterxml.jackson.annotation.JsonValue;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.inventory.vehicle.domain.enums.BodyStyle;

import java.util.HashMap;
import java.util.Map;

public enum AreaUnit {

    M2("Square Meters"),
    ARE("Are"),
    HA("Hectare");

    private static final Map<String, AreaUnit> names = new HashMap<>();
    private final String name;

    AreaUnit(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static AreaUnit getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (AreaUnit dep : AreaUnit.values()) {
            names.put(dep.name, dep);
        }
    }

}
