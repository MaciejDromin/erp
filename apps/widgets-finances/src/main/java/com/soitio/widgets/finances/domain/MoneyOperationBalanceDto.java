package com.soitio.widgets.finances.domain;

import com.soitio.commons.models.dto.finances.AmountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Month;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MoneyOperationBalanceDto {

    String uuid;
    AmountDto amount;
    int effectiveYear;
    Month effectiveMonth;
    MoneyOperationType operationType;

}
