package pl.mlisowski.finances.moneyoperation.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class MoneyOperationBalanceDto {

    String uuid;
    AmountDto amount;
    int effectiveYear;
    Month effectiveMonth;
    MoneyOperationType operationType;

}
