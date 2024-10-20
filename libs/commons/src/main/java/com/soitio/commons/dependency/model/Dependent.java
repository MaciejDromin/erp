package com.soitio.commons.dependency.model;

import java.util.HashMap;
import java.util.Map;

public enum Dependent {

    INVENTORY_ITEM("inventory.item"),
    INVENTORY_CATEGORY("inventory.category"),
    INVENTORY_ADDRESS("inventory.address"),
    INVENTORY_PROPERTY("inventory.property"),
    INVENTORY_VEHICLE("inventory.vehicle"),
    INVENTORY_CONTRACTOR("inventory.contractor"),
    INVENTORY_MAINTENANCE("inventory.maintenance"),
    INVENTORY_PART("inventory.part");

    private final String name;
    private static final Map<String, Dependent> names = new HashMap<>();

    Dependent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String name) {
        if (names.isEmpty()) initializeSet();
        return names.containsKey(name.toLowerCase());
    }

    public static Dependent getByName(String name) {
        return names.get(name);
    }

    private static void initializeSet() {
        for (Dependent dep : Dependent.values()) {
            names.put(dep.name, dep);
        }
    }

}
