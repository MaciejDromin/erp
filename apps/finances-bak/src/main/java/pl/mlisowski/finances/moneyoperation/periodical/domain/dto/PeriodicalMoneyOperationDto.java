package pl.mlisowski.finances.moneyoperation.periodical.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;

import java.time.Month;

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
