package com.soitio.commons.models.dto.finances;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.time.Month;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlannedExpensesDto {

    String id;
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
