package com.soitio.finances.plannedexpenses.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;
import com.soitio.finances.plannedexpenses.domain.PlannedExpensesStatus;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlannedExpensesDto {

    String uuid;
    AmountDto plannedAmount;
    AmountDto actualAmount;
    String operationDescription;
    int plannedYear;
    Month plannedMonth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime finalizedDate;
    PlannedExpensesStatus plannedExpensesStatus;
    MoneyOperationType operationType;
    OperationCategoryDto operationCategory;
    boolean isFinalized;

}
