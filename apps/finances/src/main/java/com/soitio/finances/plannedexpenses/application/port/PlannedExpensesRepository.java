package com.soitio.finances.plannedexpenses.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;

@Repository
public interface PlannedExpensesRepository extends JpaRepository<PlannedExpenses, String> {
}
