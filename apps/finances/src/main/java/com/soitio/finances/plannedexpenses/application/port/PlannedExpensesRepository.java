package com.soitio.finances.plannedexpenses.application.port;

import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedExpensesRepository extends JpaRepository<PlannedExpenses, String> {
}
