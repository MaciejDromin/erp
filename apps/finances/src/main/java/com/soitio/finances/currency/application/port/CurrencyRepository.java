package com.soitio.finances.currency.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.currency.domain.Currency;
import com.soitio.finances.currency.domain.CurrencyRateProj;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends OrgRepository<Currency, String>, QuerydslPredicateExecutor<Currency> {

    @Query("SELECT c.id AS id, c.code AS code, c.rate AS rate FROM Currency AS c "
        + "WHERE c.effectiveDate = (SELECT MAX(cc.effectiveDate) FROM Currency AS cc GROUP BY cc.effectiveDate "
        + "ORDER BY cc.effectiveDate DESC limit 1) AND c.code IN (:currencies) ORDER BY c.id ASC")
    List<CurrencyRateProj> findAllLatestByCode(@Param("currencies") List<String> currencies);

}
