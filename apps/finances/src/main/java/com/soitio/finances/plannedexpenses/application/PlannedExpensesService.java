package com.soitio.finances.plannedexpenses.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.utils.DateUtils;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import com.soitio.finances.plannedexpenses.application.port.PlannedExpensesRepository;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import com.soitio.finances.plannedexpenses.domain.PlannedExpensesStatus;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCompletionDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCreationDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesDto;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlannedExpensesService extends AbstractDependencyCheckService<PlannedExpenses> implements DependencyCheckService {

    private static final String SERVICE_NAME = "PlannedExpenses";
    private final PlannedExpensesRepository repository;
    private final OperationCategoryService operationCategoryService;
    private final MoneyOperationService moneyOperationService;

    public PlannedExpensesService(ObjectMapper mapper,
                                  DependencyCheckRequester dependencyCheckRequester,
                                  PlannedExpensesRepository repository,
                                  OperationCategoryService operationCategoryService,
                                  MoneyOperationService moneyOperationService) {
        super(mapper, dependencyCheckRequester);
        this.repository = repository;
        this.operationCategoryService = operationCategoryService;
        this.moneyOperationService = moneyOperationService;
    }

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
        plannedExpenses.setActualAmount(completion.getActualAmount());
        plannedExpenses.setFinalizedDate(LocalDateTime.now(ZoneOffset.UTC));
        plannedExpenses.setPlannedExpensesStatus(PlannedExpensesStatus.COMPLETED);
        return plannedExpenses;
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<String> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<String> set) {
        return repository.findAllByOperationCategoryUuidIn(set)
                .stream()
                .map(PlannedExpenses::getOperationCategory)
                .map(OperationCategory::getUuid)
                .map(id -> new DependencyCheckResult(id, true, "Operation category with id '%s' is in use!".formatted(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIds(Set<String> collect) {
        repository.deleteAllById(collect);
    }

    @Override
    protected PlannedExpenses findById(String id) {
        return repository.getReferenceById(id);
    }

    @Override
    protected PlannedExpenses mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        var opCat = fields.get("operationCategory").getObjectValue();
        var finalizedDate = fields.get("finalizedDate").getStrValue();
        return PlannedExpenses.builder()
                .uuid(fields.get("uuid").getStrValue())
                .plannedAmount(fields.get("plannedAmount").getBigNumberValue())
                .actualAmount(fields.get("actualAmount").getBigNumberValue())
                .currency(fields.get("currency").getStrValue())
                .operationDescription(fields.get("operationDescription").getStrValue())
                .operationType(MoneyOperationType.valueOf(fields.get("operationType").getStrValue()))
                .plannedExpensesStatus(PlannedExpensesStatus.valueOf(fields.get("plannedExpensesStatus").getStrValue()))
                .finalizedDate(finalizedDate == null ? null : DateUtils.localDateTimeFromString(finalizedDate))
                .plannedYear(fields.get("plannedYear").getIntValue())
                .plannedMonth(Month.valueOf(fields.get("plannedMonth").getStrValue()))
                .operationCategory(OperationCategory.builder()
                        .uuid(opCat.get("uuid").getStrValue())
                        .operationType(MoneyOperationType.valueOf(opCat.get("operationType").getStrValue()))
                        .operationName(opCat.get("operationName").getStrValue())
                        .build())
                .build();
    }

    @Override
    protected void updateEntity(PlannedExpenses entity) {
        repository.save(entity);
    }

    @Override
    protected Object mapToDto(PlannedExpenses entity) {
        return null;
    }

    public PlannedExpensesDto getPlannedExpense(String id) {
        return convertToDto(findById(id));
    }
}
