package com.soitio.finances.moneyoperation.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyOperationRepository extends OrgRepository<MoneyOperation, String> {

    List<MoneyOperation> findAllByEffectiveYearAndOrgId(int year, String orgId);

    @Query(value = "select distinct mo.effectiveYear from MoneyOperation mo")
    List<Integer> findDistinctEffectiveYearBy();

    @Query(value = "select distinct mo.effectiveMonth from MoneyOperation mo where mo.effectiveYear = ?1")
    List<Month> findDistinctEffectiveMonthByEffectiveYear(int effectiveYear);

    List<MoneyOperation> findAllByEffectiveYearAndEffectiveMonth(int balanceYear, Month balanceMonth);

    List<MoneyOperation> findAllByOperationCategoryIdIn(Set<String> ids);

}
