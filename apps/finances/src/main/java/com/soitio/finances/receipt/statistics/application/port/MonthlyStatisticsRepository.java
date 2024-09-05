package com.soitio.finances.receipt.statistics.application.port;

import com.soitio.finances.receipt.statistics.domain.MonthlyStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyStatisticsRepository extends JpaRepository<MonthlyStatistics, String> {
}
