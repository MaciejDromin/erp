package com.soitio.finances.moneyoperation.periodical.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;

@Repository
public interface PeriodicalMoneyOperationRepository
        extends JpaRepository<PeriodicalMoneyOperaion, String>, QuerydslPredicateExecutor<PeriodicalMoneyOperaion> {
}
