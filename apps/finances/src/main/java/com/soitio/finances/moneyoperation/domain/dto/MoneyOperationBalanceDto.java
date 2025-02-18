package com.soitio.finances.moneyoperation.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.models.dto.finances.MoneyOperationType;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MoneyOperationBalanceDto {

    String id;
    AmountDto amount;
    int effectiveYear;
    Month effectiveMonth;
    MoneyOperationType operationType;

}
