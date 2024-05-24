package pl.mlisowski.finances.common.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class AmountDto {

    BigDecimal value;
    String currencyCode;

}
