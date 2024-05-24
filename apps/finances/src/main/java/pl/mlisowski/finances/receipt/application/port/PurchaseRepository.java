package pl.mlisowski.finances.receipt.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.receipt.domain.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}
