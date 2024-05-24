package com.soitio.analytics.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseToAnalyzeDto {

    private String id;
    private BigDecimal amount;
    private String currency;
    private LocalDate date;
    private List<PurchaseItemToAnalyzeDto> items;

}
