package pl.mlisowski.finances.moneyoperation.periodical.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;

@Repository
public interface PeriodicalMoneyOperationRepository extends JpaRepository<PeriodicalMoneyOperaion, String> {
}
