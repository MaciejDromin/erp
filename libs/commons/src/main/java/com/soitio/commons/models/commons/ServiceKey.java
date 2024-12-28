package com.soitio.commons.models.commons;

import java.util.HashMap;
import java.util.Map;

public enum ServiceKey {
    
    FINANCES("finances"),
    INVENTORY("inventory"),
    PLANNER("planner"),
    PURCHASE_SCANNER("purchase-scanner"),
    DASHBOARD("dashboard"),
    REPORTS("reports-service"),
    WIDGETS_FINANCES("widgets-finances"),
    REPORTS_WS("reports-ws");

    private final String name;
    private static final Map<String, ServiceKey> names = new HashMap<>();

    ServiceKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String name) {
        if (names.isEmpty()) initializeMap();
        return names.containsKey(name.toLowerCase());
    }

    public static ServiceKey getByName(String name) {
        if (names.isEmpty()) initializeMap();
        return names.get(name);
    }

    private static void initializeMap() {
        for (ServiceKey sk : ServiceKey.values()) {
            names.put(sk.name, sk);
        }
    }
    
}
