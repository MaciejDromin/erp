package pl.mlisowski.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.labour.domain.dto.LabourWithQuantityDto;
import pl.mlisowski.planner.material.domain.dto.MaterialWithQuantityDto;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class InvestmentPhaseDto {

    private String id;
    private String name;
    private List<InvestmentPhaseDto> subPhases;
    private List<MaterialWithQuantityDto> materials;
    private List<LabourWithQuantityDto> labours;
    private int phaseTime;
    private AmountDto totalCost;

}
