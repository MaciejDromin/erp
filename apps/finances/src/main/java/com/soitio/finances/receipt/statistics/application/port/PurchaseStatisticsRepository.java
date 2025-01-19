package com.soitio.finances.receipt.statistics.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.receipt.statistics.domain.PurchaseStatistics;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseStatisticsRepository extends OrgRepository<PurchaseStatistics, String> {
}
