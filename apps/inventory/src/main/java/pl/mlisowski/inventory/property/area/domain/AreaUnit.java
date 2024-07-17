package pl.mlisowski.inventory.property.area.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AreaUnit {

    M2("Square Meters"),
    ARE("Are"),
    HA("Hectare");

    private final String name;

    AreaUnit(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
