package com.soitio.finances.moneyoperation.application.port;

import java.time.Year;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;

@Repository
public interface MoneyOperationRepository extends JpaRepository<MoneyOperation, String> {

    List<MoneyOperation> findAllByEffectiveYear(int year);

}
