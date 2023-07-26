package pl.mlisowski.finances.moneyoperation.periodical.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.periodical.application.port.PeriodicalMoneyOperationRepository;
import pl.mlisowski.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;
import pl.mlisowski.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import pl.mlisowski.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;
import pl.mlisowski.finances.operationcategories.application.OperationCategoryService;

@Service
@RequiredArgsConstructor
public class PeriodicalMoneyOperationService {

    private final OperationCategoryService operationCategoryService;
    private final PeriodicalMoneyOperationRepository repository;

    public Page<PeriodicalMoneyOperationDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    public void create(PeriodicalMoneyOperationCreationDto creation) {
        repository.save(createObject(creation));
    }

    private PeriodicalMoneyOperaion createObject(PeriodicalMoneyOperationCreationDto creation) {
        return PeriodicalMoneyOperaion.builder()
                .amount(creation.getAmount().getValue())
                .operationDescription(creation.getOperationDescription())
                .currency(creation.getAmount().getCurrencyCode())
                .repetitionPeriod(creation.getRepetitionPeriod())
                .operationType(creation.getOperationType())
                .nextApplicableMonth(creation.getNextApplicableMonth())
                .operationCategory(operationCategoryService.getCategoryById(creation.getOperationCategoryId()))
                .build();
    }

    private PeriodicalMoneyOperationDto from(PeriodicalMoneyOperaion periodicalMoneyOperaion) {
        var amount = periodicalMoneyOperaion.getAmount();
        return PeriodicalMoneyOperationDto.builder()
                .uuid(periodicalMoneyOperaion.getUuid())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .operationDescription(periodicalMoneyOperaion.getOperationDescription())
                .repetitionPeriod(periodicalMoneyOperaion.getRepetitionPeriod())
                .operationType(periodicalMoneyOperaion.getOperationType())
                .nextApplicableMonth(periodicalMoneyOperaion.getNextApplicableMonth())
                .operationCategory(operationCategoryService.from(periodicalMoneyOperaion.getOperationCategory()))
                .build();
    }
}
