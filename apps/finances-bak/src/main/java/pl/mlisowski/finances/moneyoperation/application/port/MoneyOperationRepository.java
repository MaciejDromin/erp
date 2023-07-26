package pl.mlisowski.finances.moneyoperation.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperation;

@Repository
public interface MoneyOperationRepository extends JpaRepository<MoneyOperation, String> {
}
