package pl.mlisowski.planner.material.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.common.model.QuantityUnit;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaterialCreationDto {

    private String name;
    private AmountDto unitAmount;
    private QuantityUnit unit;
    private String source;

}
