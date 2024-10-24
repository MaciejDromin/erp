package com.soitio.inventory.category.web;

import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.inventory.category.application.CategoryRepository;

import java.util.Set;

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

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return categoryRepository.delete(Dependent.INVENTORY_CATEGORY, ids);
    }

}
