package com.soitio.analytics.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseItemToAnalyzeDto {

    private String id;
    private BigDecimal price;
    private BigDecimal unitPrice;
    private double quantity;
    private String currency;

}
