package com.soitio.inventory.category.application;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import com.soitio.inventory.category.domain.Category;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CategoryRepository implements PanacheMongoRepository<Category> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<CategoryDto> findAll(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var categories = findAll();
        var requestedSize = params.getFirst("size");
        var size = requestedSize == null ? DEFAULT_PAGE_SIZE : Integer.parseInt(requestedSize);
        var categoryList = categories.page(pageNum, size).list();
        return PageDto.of(categoryList.stream()
                .map(this::convert)
                .toList(), categories.pageCount());
    }

    public CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .build();
    }

    public void create(CategoryDto category) {
        persist(convert(category));
    }

    private Category convert(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public Set<Category> findAllByIdsIn(Set<ObjectId> categoryIds) {
        return new HashSet<>(list("_id in ?1", categoryIds));
    }

}
