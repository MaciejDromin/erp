package pl.mlisowski.planner.labour.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.common.model.QuantityUnit;

@Builder
@AllArgsConstructor
@Getter
public class LabourCreationDto {

    private String name;
    private AmountDto rateAmount;
    private String contractorName;
    private String contractorContact;
    private QuantityUnit unit;

}
