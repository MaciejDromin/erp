package com.soitio.finances.moneyoperation.periodical.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PeriodicalMoneyOperationDto {

    String uuid;
    String operationDescription;
    AmountDto amount;
    int repetitionPeriod;
    MoneyOperationType operationType;
    Month nextApplicableMonth;
    OperationCategoryDto operationCategory;

}
