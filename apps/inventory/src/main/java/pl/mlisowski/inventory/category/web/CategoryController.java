package pl.mlisowski.inventory.category.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.category.application.CategoryRepository;
import pl.mlisowski.inventory.category.domain.dto.CategoryDto;
import pl.mlisowski.inventory.common.PageDto;

@Path("/inventory/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GET
    public PageDto<CategoryDto> getAllCategories(@Context UriInfo uriInfo) {
        return categoryRepository.findAll(uriInfo);
    }

    @POST
    public void addCategory(CategoryDto category) {
        categoryRepository.create(category);
    }

}
