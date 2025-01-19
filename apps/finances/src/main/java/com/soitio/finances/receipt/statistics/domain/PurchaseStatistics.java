package com.soitio.finances.receipt.statistics.domain;

import com.soitio.finances.common.persistence.BaseEntity;
import com.soitio.finances.receipt.domain.Purchase;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(indexes = {
    @Index(name = "org_id", columnList = "id, orgId", unique = true)
})
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
