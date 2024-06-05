package pl.mlisowski.selfregister.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeightsDto(
        @JsonProperty("Passing") int passing,
        @JsonProperty("Warning") int warning) {
}
