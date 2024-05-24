package pl.mlisowski.finances.receipt.statistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.receipt.domain.Purchase;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PurchaseStatistics extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal mean;
    private BigDecimal median;
    private BigDecimal variation;
    private BigDecimal standardDeviation;
    private BigDecimal total;
    private int totalItems;

    @ManyToOne
    @JoinColumn(name = "monthly_statistics_id")
    private MonthlyStatistics monthlyStatistics;

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
        if (purchase.getStatistics() == null) {
            purchase.setStatistics(this);
        }
    }

    public void setMonthlyStatistics(MonthlyStatistics statistics) {
        this.monthlyStatistics = statistics;
        statistics.addPurchaseStatistics(this);
    }

}
