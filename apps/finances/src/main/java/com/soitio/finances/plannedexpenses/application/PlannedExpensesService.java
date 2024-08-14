package com.soitio.finances.plannedexpenses.application;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.plannedexpenses.application.port.PlannedExpensesRepository;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import com.soitio.finances.plannedexpenses.domain.PlannedExpensesStatus;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCompletionDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCreationDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlannedExpensesService {

    private final PlannedExpensesRepository repository;
    private final OperationCategoryService operationCategoryService;
    private final MoneyOperationService moneyOperationService;

    public void create(PlannedExpensesCreationDto creation) {
        repository.save(convert(creation));
    }

    // TODO: Extract to factory
    private PlannedExpenses convert(PlannedExpensesCreationDto creation) {
        var operationCategory = operationCategoryService.getCategoryById(creation.getOperationCategoryId());
        return PlannedExpenses.builder()
                .plannedAmount(creation.getPlannedAmount().getValue())
                .currency(creation.getPlannedAmount().getCurrencyCode())
                .operationDescription(creation.getOperationDescription())
                .operationType(creation.getOperationType())
                .plannedYear(creation.getPlannedYear())
                .plannedMonth(creation.getPlannedMonth())
                .operationCategory(operationCategory)
                .build();
    }

    public Page<PlannedExpensesDto> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::convertToDto);
    }

    // TODO: Extract to factory
    private PlannedExpensesDto convertToDto(PlannedExpenses plannedExpenses) {
        return PlannedExpensesDto.builder()
                .uuid(plannedExpenses.getUuid())
                .plannedAmount(AmountDto.builder()
                        .value(plannedExpenses.getPlannedAmount().getAmount())
                        .currencyCode(plannedExpenses.getPlannedAmount().getCurrencyUnit().getCode())
                        .build())
                .actualAmount(plannedExpenses.isFinalized() ? AmountDto.builder()
                        .value(plannedExpenses.getActualAmount().getAmount())
                        .currencyCode(plannedExpenses.getActualAmount().getCurrencyUnit().getCode())
                        .build() : null)
                .operationDescription(plannedExpenses.getOperationDescription())
                .plannedYear(plannedExpenses.getPlannedYear())
                .plannedMonth(plannedExpenses.getPlannedMonth())
                .finalizedDate(plannedExpenses.getFinalizedDate())
                .plannedExpensesStatus(plannedExpenses.getPlannedExpensesStatus())
                .operationType(plannedExpenses.getOperationType())
                .operationCategory(operationCategoryService.from(plannedExpenses.getOperationCategory()))
                .isFinalized(plannedExpenses.isFinalized())
                .build();
    }

    public void abandon(String plannedExpenseId) {
        repository.save(updateForAbandon(repository.getReferenceById(plannedExpenseId)));
    }

    private PlannedExpenses updateForAbandon(PlannedExpenses plannedExpenses) {
        plannedExpenses.setPlannedExpensesStatus(PlannedExpensesStatus.ABANDONED);
        plannedExpenses.setFinalizedDate(LocalDateTime.now(ZoneOffset.UTC));
        return plannedExpenses;
    }

    public void complete(String plannedExpenseId, PlannedExpensesCompletionDto completion) {
        moneyOperationService.registerPlannedExpense(
                repository.save(updateForCompletion(
                        repository.getReferenceById(plannedExpenseId), completion)));
    }

    private PlannedExpenses updateForCompletion(PlannedExpenses plannedExpenses, PlannedExpensesCompletionDto completion) {
        plannedExpenses.setActualAmount(completion.getActualAmount().getValue());
        plannedExpenses.setFinalizedDate(LocalDateTime.now(ZoneOffset.UTC));
        plannedExpenses.setPlannedExpensesStatus(PlannedExpensesStatus.COMPLETED);
        return plannedExpenses;
    }
}