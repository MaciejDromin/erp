package pl.mlisowski.planner.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class AmountDto {

    BigDecimal value;
    String currencyCode;

}
