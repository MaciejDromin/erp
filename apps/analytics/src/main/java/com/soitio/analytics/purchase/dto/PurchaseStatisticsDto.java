package com.soitio.analytics.purchase.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Builder
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseStatisticsDto {

    private String id;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal mean;
    private BigDecimal median;
    private BigDecimal variation;
    private BigDecimal standardDeviation;
    private BigDecimal total;
    private int totalItems;

}
