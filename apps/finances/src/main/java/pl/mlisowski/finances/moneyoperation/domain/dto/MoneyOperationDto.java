package pl.mlisowski.finances.moneyoperation.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;

@Value
@Builder
public class MoneyOperationDto {

    String uuid;
    AmountDto amount;
    String operationDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    ZonedDateTime effectiveDate;
    MoneyOperationType operationType;
    OperationCategoryDto operationCategory;

}
