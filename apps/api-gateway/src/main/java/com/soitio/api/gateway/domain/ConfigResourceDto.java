package com.soitio.api.gateway.domain;

import org.bson.types.ObjectId;

public record ConfigResourceDto(ObjectId id, String key, Object value) {
}
