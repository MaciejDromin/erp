package pl.mlisowski.finances.objectvalues.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ObjectValueCreationDto {

    String objectId;
    String amount;
    String currencyCode;

}
