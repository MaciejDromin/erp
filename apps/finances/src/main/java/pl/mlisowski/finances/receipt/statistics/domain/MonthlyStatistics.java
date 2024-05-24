package pl.mlisowski.finances.receipt.statistics.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.finances.common.persistence.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(indexes = {
    @Index(name = "msperiod", columnList = "month, year")
})
public class MonthlyStatistics extends BaseEntity {

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

    @Builder.Default
    @OneToMany(mappedBy = "monthlyStatistics", orphanRemoval = true)
    private List<PurchaseStatistics> purchases = new ArrayList<>();
    private Month month;
    private int year;
    private LocalDate date;

    public void addPurchaseStatistics(PurchaseStatistics purchaseStatistics) {
        if (!this.purchases.contains(purchaseStatistics)) {
            this.purchases.add(purchaseStatistics);
        }
    }

}
