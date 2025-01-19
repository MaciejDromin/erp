package com.soitio.finances.plannedexpenses.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedExpensesRepository extends OrgRepository<PlannedExpenses, String> {

    List<PlannedExpenses> findAllByOperationCategoryIdIn(Set<String> set);

    Page<PlannedExpenses> findAllPageableByPlannedYearAndPlannedMonthAndOrgId(Pageable pageable, Integer integer, Month month, String orgId);
}
