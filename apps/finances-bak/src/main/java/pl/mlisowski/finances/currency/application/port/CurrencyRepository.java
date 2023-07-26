package pl.mlisowski.finances.currency.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.currency.domain.Currency;
import pl.mlisowski.finances.currency.domain.CurrencyRateProj;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    @Query("SELECT c.uuid AS uuid, c.code AS code, c.rate AS rate FROM Currency AS c WHERE c.effectiveDate = (SELECT MAX(cc.effectiveDate) FROM Currency AS cc GROUP BY cc.effectiveDate ORDER BY cc.effectiveDate DESC limit 1) AND c.code IN (:currencies) ORDER BY c.uuid ASC")
    List<CurrencyRateProj> findAllLatestByCode(@Param("currencies") List<String> currencies);
}
