package com.soitio.finances.moneyoperation.periodical.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;

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
