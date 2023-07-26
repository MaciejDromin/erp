package pl.mlisowski.finances.operationcategories.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.operationcategories.application.port.OperationCategoryRepository;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;
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

    public Page<OperationCategoryDto> getPage(Pageable pageable) {
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

    public void testing() {
        QOperationCategory
    }

}
