package com.soitio.finances.receipt.statistics.application.port;

import com.soitio.finances.receipt.statistics.domain.PurchaseStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseStatisticsRepository extends JpaRepository<PurchaseStatistics, String> {
}
