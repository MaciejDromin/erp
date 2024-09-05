package com.soitio.finances.moneyoperation.periodical.application.port;

import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalMoneyOperationRepository
        extends JpaRepository<PeriodicalMoneyOperaion, String>, QuerydslPredicateExecutor<PeriodicalMoneyOperaion> {
}
