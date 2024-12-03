package com.soitio.finances.plannedexpenses.application.port;

import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedExpensesRepository extends JpaRepository<PlannedExpenses, String> {

    List<PlannedExpenses> findAllByOperationCategoryIdIn(Set<String> set);

    Page<PlannedExpenses> findAllPageableByPlannedYearAndPlannedMonth(Pageable pageable, Integer integer, Month month);
}
