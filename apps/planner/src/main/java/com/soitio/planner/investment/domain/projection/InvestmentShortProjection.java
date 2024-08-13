package com.soitio.planner.investment.domain.projection;

import io.quarkus.mongodb.panache.common.ProjectionFor;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.types.ObjectId;
import com.soitio.planner.investment.domain.Investment;

import java.math.BigDecimal;

@ProjectionFor(Investment.class)
@RegisterForReflection
public record InvestmentShortProjection(ObjectId _id,
                                        String name,
                                        int totalEstimatedTime,
                                        BigDecimal totalCost,
                                        String currency) {

    @Override
    public ObjectId _id() {
        return _id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int totalEstimatedTime() {
        return totalEstimatedTime;
    }

    @Override
    public BigDecimal totalCost() {
        return totalCost;
    }

    @Override
    public String currency() {
        return currency;
    }

}
