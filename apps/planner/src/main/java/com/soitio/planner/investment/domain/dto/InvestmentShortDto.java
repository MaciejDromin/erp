package com.soitio.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.soitio.planner.common.dto.AmountDto;

@Builder
@AllArgsConstructor
@Getter
public class InvestmentShortDto {

    private String id;
    private String name;
    private int totalEstimatedTime;
    private AmountDto totalCost;

}
