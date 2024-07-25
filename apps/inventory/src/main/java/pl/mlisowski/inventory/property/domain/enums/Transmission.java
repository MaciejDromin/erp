package pl.mlisowski.inventory.property.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Transmission {

    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String name;

    Transmission(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
