package pl.mlisowski.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FuelType {

    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    LPG("LPG"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric"),
    HYDROGEN("Hydrogen");

    private final String name;

    FuelType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
