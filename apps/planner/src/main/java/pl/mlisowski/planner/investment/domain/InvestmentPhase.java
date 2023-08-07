package pl.mlisowski.planner.investment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.mlisowski.planner.labour.domain.LabourWithQuantity;
import pl.mlisowski.planner.material.domain.MaterialWithQuantity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentPhase {

    private Object id;
    private String name;

    @Builder.Default
    private List<InvestmentPhase> subPhases = new ArrayList<>();

    @Builder.Default
    private List<MaterialWithQuantity> materials = new ArrayList<>();

    @Builder.Default
    private List<LabourWithQuantity> labour = new ArrayList<>();

    private int phaseTime;
    private BigDecimal totalCost;
    private String currency;

    @BsonIgnore
    public Money getTotalCostMoney() {
        return Money.of(CurrencyUnit.of(this.currency), this.totalCost, RoundingMode.DOWN);
    }

}
