package com.soitio.selfregister.self.register.quarkus.runtime.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeightsDto(
        @JsonProperty("Passing") int passing,
        @JsonProperty("Warning") int warning) {
}
