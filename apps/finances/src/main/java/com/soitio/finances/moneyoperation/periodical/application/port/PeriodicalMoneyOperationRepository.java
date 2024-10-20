package com.soitio.finances.moneyoperation.periodical.application.port;

import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperation;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalMoneyOperationRepository
        extends JpaRepository<PeriodicalMoneyOperation, String>, QuerydslPredicateExecutor<PeriodicalMoneyOperation> {

    List<PeriodicalMoneyOperation> findAllByOperationCategoryUuidIn(Set<String> id);

}
