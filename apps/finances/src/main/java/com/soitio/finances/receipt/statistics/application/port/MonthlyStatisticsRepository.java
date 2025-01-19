package com.soitio.finances.receipt.statistics.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.receipt.statistics.domain.MonthlyStatistics;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyStatisticsRepository extends OrgRepository<MonthlyStatistics, String> {
}
