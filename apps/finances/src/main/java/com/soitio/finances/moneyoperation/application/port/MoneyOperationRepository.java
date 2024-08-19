package com.soitio.finances.moneyoperation.application.port;

import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import java.time.Month;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyOperationRepository extends JpaRepository<MoneyOperation, String> {

    List<MoneyOperation> findAllByEffectiveYear(int year);

    @Query(value = "select distinct mo.effectiveYear from MoneyOperation mo")
    List<Integer> findDistinctEffectiveYearBy();

    @Query(value = "select distinct mo.effectiveMonth from MoneyOperation mo where mo.effectiveYear = ?1")
    List<Month> findDistinctEffectiveMonthByEffectiveYear(int effectiveYear);

}
