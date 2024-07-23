package pl.mlisowski.finances.objectvalues.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.objectvalues.domain.ObjectType;

@Value
@Builder
public class ObjectValueCreationDto {

    String objectId;
    String amount;
    String currencyCode;
    ObjectType objectType;

}
