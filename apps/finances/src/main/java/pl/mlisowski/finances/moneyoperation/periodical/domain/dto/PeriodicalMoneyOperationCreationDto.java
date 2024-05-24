package pl.mlisowski.finances.moneyoperation.periodical.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class PeriodicalMoneyOperationCreationDto {

    AmountDto amount;
    String operationDescription;
    MoneyOperationType operationType;
    Month nextApplicableMonth;
    int repetitionPeriod;
    String operationCategoryId;

}
