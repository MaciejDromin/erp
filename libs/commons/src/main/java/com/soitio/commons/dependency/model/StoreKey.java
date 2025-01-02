package com.soitio.commons.dependency.model;

import java.util.Set;

public record StoreKey(String id, String key, Set<String> value) {
}
