package com.soitio.finances.receipt.statistics.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soitio.finances.receipt.statistics.domain.PurchaseStatistics;

@Repository
public interface PurchaseStatisticsRepository extends JpaRepository<PurchaseStatistics, String> {
}