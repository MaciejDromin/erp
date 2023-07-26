package pl.mlisowski.finances.operationcategories.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class OperationCategoryCreationDto {

    MoneyOperationType operationType;
    String operationName;

}
