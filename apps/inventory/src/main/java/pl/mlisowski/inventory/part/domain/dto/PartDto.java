package pl.mlisowski.inventory.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class PartDto {

    private String name;
    private String partNumber;

}
