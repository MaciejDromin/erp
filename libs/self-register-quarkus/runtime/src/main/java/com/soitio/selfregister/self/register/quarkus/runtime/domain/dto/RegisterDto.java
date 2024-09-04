package com.soitio.selfregister.self.register.quarkus.runtime.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterDto {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Port")
    private int port;
    @JsonProperty("Check")
    private CheckDto check;
    @JsonProperty("Weights")
    private WeightsDto weights;

}
