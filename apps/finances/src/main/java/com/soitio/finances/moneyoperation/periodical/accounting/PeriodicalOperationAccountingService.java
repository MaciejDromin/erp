package com.soitio.finances.moneyoperation.periodical.accounting;

import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import com.soitio.finances.moneyoperation.periodical.application.PeriodicalMoneyOperationService;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeriodicalOperationAccountingService {

    private final PeriodicalMoneyOperationService periodicalMoneyOperationService;
    private final MoneyOperationService moneyOperationService;

    public void accountOperationsForCurrentMonth() {
        var accountingMonth = LocalDate.now().getMonth();
        log.info("Searching operations for month: {}", accountingMonth);
        List<PeriodicalMoneyOperaion> operationList = periodicalMoneyOperationService.getOperationsForMonth(accountingMonth);
        List<MoneyOperation> converted = operationList.stream()
                .map(this::convertToMoneyOperation)
                .toList();
        moneyOperationService.saveAll(converted);
        operationList.forEach(operation
                -> operation.setNextApplicableMonth(Month.of(getMonthValue(accountingMonth, operation.getRepetitionPeriod()))));
        periodicalMoneyOperationService.saveAll(operationList);
    }

    private MoneyOperation convertToMoneyOperation(PeriodicalMoneyOperaion periodicalMoneyOperaion) {
        LocalDateTime effectiveDate = LocalDateTime.now(ZoneOffset.UTC);
        return MoneyOperation.builder()
                .amount(periodicalMoneyOperaion.getAmount().getAmount())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationDescription(periodicalMoneyOperaion.getOperationDescription())
                .currency(periodicalMoneyOperaion.getAmount().getCurrencyUnit().getCode())
                .operationType(periodicalMoneyOperaion.getOperationType())
                .operationCategory(periodicalMoneyOperaion.getOperationCategory())
                .build();
    }

    private int getMonthValue(Month currentMonth, int nextPeriod) {
        int currentMonthOrdinal = currentMonth.getValue();
        if (nextPeriod == 12) return currentMonthOrdinal;
        if (currentMonthOrdinal + nextPeriod > 12) {
            return currentMonthOrdinal + nextPeriod - 12;
        }
        return currentMonthOrdinal + nextPeriod;
    }

}
