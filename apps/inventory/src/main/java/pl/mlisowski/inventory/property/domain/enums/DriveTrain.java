package pl.mlisowski.inventory.property.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DriveTrain {

    FWD("Front Wheel Drive"),
    RWD("Rear Wheel Drive"),
    AWD("All Wheel Drive"),
    AWD_FWD("AWD W/FWD"),
    AWD_RWD("AWD W/RWD");

    private final String name;

    DriveTrain(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
