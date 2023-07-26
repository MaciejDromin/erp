package pl.mlisowski.finances.objectvalues.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;

@Value
@Builder
public class TotalObjectsValueDto {

    int objectsCount;
    AmountDto totalAmount;

}
