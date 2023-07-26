package pl.mlisowski.finances.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CurrencyEntryDto {

    private String currency;
    private String code;
    private BigDecimal mid;

}
