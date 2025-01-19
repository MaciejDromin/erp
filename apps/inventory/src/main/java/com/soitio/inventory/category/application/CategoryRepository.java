package com.soitio.inventory.category.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import com.soitio.inventory.category.domain.Category;

@Slf4j
@Singleton
public class CategoryRepository extends AbstractDependencyCheckRepo<Category> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public CategoryRepository(ObjectMapper mapper,
                              DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    @Override
    protected Category mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return Category.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .build();
    }

    public PageDto<CategoryDto> findAll(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var categories = findAllByOrgId(orgId);
        var requestedSize = params.getFirst("size");
        var size = requestedSize == null ? DEFAULT_PAGE_SIZE : Integer.parseInt(requestedSize);
        var categoryList = categories.page(pageNum, size).list();
        return PageDto.of(categoryList.stream()
                .map(this::convert)
                .toList(), categories.pageCount());
    }

    public void create(CategoryDto category, String orgId) {
        Category toSave = convert(category, orgId);
        persist(toSave);
    }

    public CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .build();
    }

    private Category convert(CategoryDto categoryDto, String orgId) {
        return Category.builder()
                .name(categoryDto.getName())
                .orgId(orgId)
                .build();
    }

    public CategoryDto findOne(String categoryId, String orgId) {
        return convert(findByIdAndOrgId(categoryId, orgId));
    }

}
