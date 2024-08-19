package com.soitio.finances.moneyoperation.application;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.application.port.MoneyOperationRepository;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationBalanceDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationDto;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyOperationService {

    private final OperationCategoryService operationCategoryService;
    private final MoneyOperationRepository repository;

    public void create(MoneyOperationCreationDto creation) {
        repository.save(createObject(creation));
    }

    private MoneyOperation createObject(MoneyOperationCreationDto creation) {
        LocalDateTime effectiveDate = LocalDateTime.now(ZoneOffset.UTC);
        return MoneyOperation.builder()
                .amount(creation.getAmount().getValue())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationDescription(creation.getOperationDescription())
                .currency(creation.getAmount().getCurrencyCode())
                .operationType(creation.getOperationType())
                .operationCategory(operationCategoryService.getCategoryById(creation.getOperationCategoryId()))
                .build();
    }

    public Page<MoneyOperationDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    private MoneyOperationDto from(MoneyOperation moneyOperation) {
        var amount = moneyOperation.getAmount();
        return MoneyOperationDto.builder()
                .uuid(moneyOperation.getUuid())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .operationDescription(moneyOperation.getOperationDescription())
                .effectiveDate(moneyOperation.getEffectiveDate())
                .operationType(moneyOperation.getOperationType())
                .operationCategory(operationCategoryService.from(moneyOperation.getOperationCategory()))
                .build();
    }

    public void registerPlannedExpense(PlannedExpenses plannedExpenses) {
        repository.save(createFromPlannedExpense(plannedExpenses));
    }

    private MoneyOperation createFromPlannedExpense(PlannedExpenses plannedExpenses) {
        LocalDateTime effectiveDate = LocalDateTime.now(ZoneOffset.UTC);
        return MoneyOperation.builder()
                .amount(plannedExpenses.getActualAmount().getAmount())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationDescription(plannedExpenses.getOperationDescription())
                .currency(plannedExpenses.getActualAmount().getCurrencyUnit().getCode())
                .operationType(plannedExpenses.getOperationType())
                .operationCategory(plannedExpenses.getOperationCategory())
                .build();
    }

    public void saveAll(List<MoneyOperation> converted) {
        repository.saveAll(converted);
    }

    public List<MoneyOperationBalanceDto> getForBalance(int balanceYear) {
        return repository.findAllByEffectiveYear(balanceYear).stream()
                .map(this::toBalance)
                .toList();
    }

    private MoneyOperationBalanceDto toBalance(MoneyOperation moneyOperation) {
        return MoneyOperationBalanceDto.builder()
                .uuid(moneyOperation.getUuid())
                .amount(AmountDto.of(moneyOperation.getAmount().getAmount(),
                        moneyOperation.getAmount().getCurrencyUnit().getCode()))
                .effectiveYear(moneyOperation.getEffectiveYear())
                .effectiveMonth(moneyOperation.getEffectiveMonth())
                .operationType(moneyOperation.getOperationType())
                .build();
    }

    public List<Integer> findDistinctEffectiveYears() {
        return repository.findDistinctEffectiveYearBy();
    }

    public List<Month> findDistinctEffectiveMonths(int year) {
        return repository.findDistinctEffectiveMonthByEffectiveYear(year);
    }

}
