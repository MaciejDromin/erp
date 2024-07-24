package com.soitio.widgets.common.domain.data;

import com.fasterxml.jackson.annotation.JsonValue;

public record Rgba(int red, int green, int blue, double alpha) {

    @Override
    @JsonValue
    public String toString() {
        return "rgba(%d, %d, %d, %.2f)".formatted(red, green, blue, alpha);
    }
}
