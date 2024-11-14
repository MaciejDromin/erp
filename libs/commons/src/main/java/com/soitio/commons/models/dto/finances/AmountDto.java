package com.soitio.commons.models.dto.finances;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class AmountDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal value;
    private String currencyCode;

}
