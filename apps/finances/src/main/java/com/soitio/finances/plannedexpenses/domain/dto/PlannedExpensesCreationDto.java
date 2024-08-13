package com.soitio.finances.plannedexpenses.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;

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
