package com.soitio.finances.moneyoperation.periodical.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperation;
import java.util.List;
import java.util.Set;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalMoneyOperationRepository
        extends OrgRepository<PeriodicalMoneyOperation, String>, QuerydslPredicateExecutor<PeriodicalMoneyOperation> {

    List<PeriodicalMoneyOperation> findAllByOperationCategoryIdIn(Set<String> id);

}
