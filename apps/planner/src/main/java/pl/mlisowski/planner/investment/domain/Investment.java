package pl.mlisowski.planner.investment.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@MongoEntity(collection = "Investment")
public class Investment {

    private ObjectId id;
    private String name;

    @Builder.Default
    private List<InvestmentPhase> investmentPhases = new ArrayList<>();
    private int totalEstimatedTime;

    private BigDecimal totalCost;
    private String currency;

    @BsonIgnore
    public Money getTotalCostMoney() {
        return Money.of(CurrencyUnit.of(this.currency), this.totalCost, RoundingMode.DOWN);
    }

}
