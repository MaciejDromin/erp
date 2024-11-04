package com.soitio.finances.operationcategories.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.application.port.OperationCategoryRepository;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import com.soitio.finances.operationcategories.domain.QOperationCategory;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryCreationDto;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperationCategoryService extends AbstractDependencyCheckService<OperationCategory> {

    private final OperationCategoryRepository repository;

    public OperationCategoryService(ObjectMapper mapper,
                                    DependencyCheckRequester dependencyCheckRequester,
                                    OperationCategoryRepository repository) {
        super(mapper, dependencyCheckRequester);
        this.repository = repository;
    }

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
        if (category == null) return null;
        return OperationCategoryDto.builder()
                .uuid(category.getUuid())
                .operationName(category.getOperationName())
                .operationType(category.getOperationType())
                .build();
    }

    public OperationCategory getCategoryById(String operationCategory) {
        return repository.getReferenceById(operationCategory);
    }

    @Override
    public void deleteByIds(Set<String> collect) {
        repository.deleteAllById(collect);
    }

    @Override
    protected OperationCategory findById(String id) {
        return repository.getReferenceById(id);
    }

    @Override
    protected OperationCategory mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return OperationCategory.builder()
                .uuid(fields.get("uuid").getStrValue())
                .operationType(MoneyOperationType.valueOf(fields.get("operationType").getStrValue()))
                .operationName(fields.get("operationName").getStrValue())
                .build();
    }

    @Override
    protected void updateEntity(OperationCategory entity) {
        repository.save(entity);
    }

}
