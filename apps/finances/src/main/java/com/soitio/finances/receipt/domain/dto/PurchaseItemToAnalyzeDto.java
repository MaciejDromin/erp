package com.soitio.finances.receipt.domain.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PurchaseItemToAnalyzeDto {

    private String id;
    private BigDecimal price;
    private BigDecimal unitPrice;
    private double quantity;
    private String currency;

}
