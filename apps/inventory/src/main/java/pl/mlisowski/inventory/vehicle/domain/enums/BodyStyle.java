package pl.mlisowski.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BodyStyle {

    STATION_WAGON("Station Wagon"),
    SUV("SUV"),
    COUPE("Coupe"),
    HATCHBACK("Hatchback"),
    PICKUP("Pickup"),
    SEDAN("Sedan");

    private final String name;

    BodyStyle(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
