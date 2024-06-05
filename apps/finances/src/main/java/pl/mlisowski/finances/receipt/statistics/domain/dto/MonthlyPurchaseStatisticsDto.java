package pl.mlisowski.finances.receipt.statistics.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
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
public class MonthlyPurchaseStatisticsDto {

    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal mean;
    private BigDecimal median;
    private BigDecimal variation;
    private BigDecimal standardDeviation;
    private BigDecimal total;
    private int totalPurchases;
    private int totalItems;
    private double averageItemPerPurchase;
    private List<PurchaseStatisticsDto> purchases;
    private Month month;
    private LocalDate date;

}
