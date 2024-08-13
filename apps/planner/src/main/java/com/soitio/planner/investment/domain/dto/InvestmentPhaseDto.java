package com.soitio.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.planner.labour.domain.dto.LabourWithQuantityDto;
import com.soitio.planner.material.domain.dto.MaterialWithQuantityDto;

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
