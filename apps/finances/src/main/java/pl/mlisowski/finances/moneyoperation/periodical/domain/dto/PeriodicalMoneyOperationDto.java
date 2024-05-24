package pl.mlisowski.finances.moneyoperation.periodical.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;

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
