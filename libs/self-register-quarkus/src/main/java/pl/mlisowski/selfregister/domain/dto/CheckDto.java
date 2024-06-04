package pl.mlisowski.selfregister.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckDto {

    @JsonProperty("HTTP")
    private String http;
    @Builder.Default
    @JsonProperty("Interval")
    private String interval = "5s";
    @Builder.Default
    @JsonProperty("Timeout")
    private String timeout = "1s";
    @Builder.Default
    @JsonProperty("DeregisterCriticalServiceAfter")
    private String deregisterPolicy = "3m";

}
