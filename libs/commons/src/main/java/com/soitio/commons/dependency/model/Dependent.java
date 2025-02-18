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
    INVENTORY_PART("inventory.part"),
    FINANCES_CATEGORY("finances.category"),
    FINANCES_MONEY_OPERATION("finances.moneyoperation"),
    FINANCES_PERIODICAL("finances.periodical"),
    FINANCES_PLANNED_EXPENSES("finances.plannedexpenses"),
    FINANCES_OBJECT_VALUE("finances.objectvalue");

    private final String name;
    private static final Map<String, Dependent> names = new HashMap<>();

    Dependent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String name) {
        if (names.isEmpty()) initializeMap();
        return names.containsKey(name.toLowerCase());
    }

    public static Dependent getByName(String name) {
        return names.get(name);
    }

    private static void initializeMap() {
        for (Dependent dep : Dependent.values()) {
            names.put(dep.name, dep);
        }
    }

}
