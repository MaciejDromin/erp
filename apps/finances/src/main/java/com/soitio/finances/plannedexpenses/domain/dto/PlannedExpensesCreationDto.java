package com.soitio.finances.plannedexpenses.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlannedExpensesCreationDto {

    AmountDto plannedAmount;
    String operationDescription;
    MoneyOperationType operationType;
    int plannedYear;
    Month plannedMonth;
    String operationCategoryId;

}
