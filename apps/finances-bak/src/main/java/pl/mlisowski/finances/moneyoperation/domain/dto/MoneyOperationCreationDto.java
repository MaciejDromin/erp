package pl.mlisowski.finances.moneyoperation.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class MoneyOperationCreationDto {

    AmountDto amount;
    String operationDescription;
    MoneyOperationType operationType;
    String operationCategoryId;

}
