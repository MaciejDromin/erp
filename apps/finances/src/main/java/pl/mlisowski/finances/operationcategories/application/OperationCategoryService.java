package pl.mlisowski.finances.operationcategories.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.application.port.OperationCategoryRepository;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;
import pl.mlisowski.finances.operationcategories.domain.QOperationCategory;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryCreationDto;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;

@Service
@RequiredArgsConstructor
public class OperationCategoryService {

    private final OperationCategoryRepository repository;

    public void create(OperationCategoryCreationDto creation) {
        repository.save(createEntity(creation));
    }

    private OperationCategory createEntity(OperationCategoryCreationDto creation) {
        return OperationCategory.builder()
                .operationType(creation.getOperationType())
                .operationName(creation.getOperationName())
                .build();
    }

    public Page<OperationCategoryDto> getPage(Map<String, String> queryParams) {
        var pageable = Pageable.ofSize(20).withPage(Integer.parseInt(queryParams.get("page")));
        if (queryParams.containsKey("operationType")) {
            var operationType = MoneyOperationType.valueOf(queryParams.get("operationType"));
            QOperationCategory category = QOperationCategory.operationCategory;
            var search = category.operationType.eq(operationType);
            return repository.findAll(search, pageable).map(this::from);
        }
        return repository.findAll(pageable).map(this::from);
    }

    public OperationCategoryDto from(OperationCategory category) {
        return OperationCategoryDto.builder()
                .uuid(category.getUuid())
                .operationName(category.getOperationName())
                .operationType(category.getOperationType())
                .build();
    }

    public OperationCategory getCategoryById(String operationCategory) {
        return repository.getReferenceById(operationCategory);
    }

    public List<OperationCategory> testing() {
        QOperationCategory category = QOperationCategory.operationCategory;
        List<OperationCategory> ret = new ArrayList<>();
        var categories = category.operationType.eq(MoneyOperationType.EXPENSES);
        repository.findAll(categories).forEach(ret::add);
        return ret;
    }

}
