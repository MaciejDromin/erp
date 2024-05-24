package pl.mlisowski.finances.receipt.statistics.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.receipt.statistics.domain.MonthlyStatistics;

@Repository
public interface MonthlyStatisticsRepository extends JpaRepository<MonthlyStatistics, String> {
}
