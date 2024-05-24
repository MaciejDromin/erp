package pl.mlisowski.finances.receipt.statistics.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.receipt.statistics.domain.PurchaseStatistics;

@Repository
public interface PurchaseStatisticsRepository extends JpaRepository<PurchaseStatistics, String> {
}
