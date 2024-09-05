package com.soitio.finances.plannedexpenses.domain.dto;

import com.soitio.finances.common.dto.AmountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlannedExpensesCompletionDto {

    private AmountDto actualAmount;

}
