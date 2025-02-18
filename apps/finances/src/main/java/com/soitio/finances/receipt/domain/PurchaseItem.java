package com.soitio.finances.receipt.domain;

import com.soitio.finances.common.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
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
public class PurchaseItem extends BaseEntity {

    private String name;
    @Enumerated(EnumType.ORDINAL)
    private ItemUnit unit;
    private BigDecimal price;
    private BigDecimal unitPrice;
    private double quantity;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.price, RoundingMode.DOWN);
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
