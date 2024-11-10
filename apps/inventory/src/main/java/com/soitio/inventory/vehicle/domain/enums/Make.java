package com.soitio.inventory.vehicle.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum Make {

    BMW("BMW"),
    VOLKSWAGEN("Volkswagen"),
    AUDI("Audi"),
    FORD("Ford"),
    MERCEDES_BENZ("Mercedes-Benz"),
    OPEL("Opel"),
    SKODA("Skoda"),
    RENAULT("Renault"),
    PEUGEOT("Peugeot"),
    ALFA_ROMEO("Alfa Romeo"),
    VOLVO("Volvo"),
    FIAT("Fiat"),
    TESLA("Tesla"),
    JAGUAR("Jaguar"),
    JEEP("Jeep"),
    KIA("Kia"),
    LAND_ROVER("Land Rover"),
    LEXUS("Lexus"),
    MAZDA("Mazda"),
    NISSAN("Nissan"),
    DODGE("Dodge"),
    SUBARU("Subaru"),
    SUZUKI("Suzuki"),
    TOYOTA("Toyota");

    private final String name;
    private static final Map<String, Make> names = new HashMap<>();

    Make(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static Make getEnum(String val) {
        if (names.isEmpty()) initializeMap();
        return names.get(val);
    }

    private static void initializeMap() {
        for (Make style : values()) {
            names.put(style.name, style);
        }
    }

}
