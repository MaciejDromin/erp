package com.soitio.finances.plannedexpenses.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
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
