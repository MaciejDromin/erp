package com.soitio.finances.receipt.domain;

import com.soitio.finances.common.persistence.BaseEntity;
import com.soitio.finances.receipt.statistics.domain.PurchaseStatistics;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(indexes = {
    @Index(name = "org_id", columnList = "id, orgId", unique = true)
})
public class Purchase extends BaseEntity {

    private String address;
    private BigDecimal amount;
    private String currency;
    private String source;
    @Builder.Default
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();
    private LocalDateTime date;

    @OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private PurchaseStatistics statistics;

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.amount, RoundingMode.DOWN);
    }

    public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
        purchaseItems.forEach(purchaseItem -> purchaseItem.setPurchase(this));
    }

    public void setStatistics(PurchaseStatistics statistics) {
        this.statistics = statistics;
        if (statistics.getPurchase() == null) {
            statistics.setPurchase(this);
        }
    }

    public BigDecimal getRawAmount() {
        return this.amount;
    }

}
