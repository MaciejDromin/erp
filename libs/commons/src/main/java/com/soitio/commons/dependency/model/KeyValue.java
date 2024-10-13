package com.soitio.commons.dependency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class KeyValue {

    @JsonProperty("Value")
    private String value;

}
