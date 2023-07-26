package pl.mlisowski.finances.selfregister.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RegisterDto {

    private int port;
    private String serviceName;
    private String address;

}
