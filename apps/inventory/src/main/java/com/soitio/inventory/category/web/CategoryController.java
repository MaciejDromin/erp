package com.soitio.inventory.category.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.inventory.category.application.CategoryRepository;
import com.soitio.inventory.category.domain.dto.CategoryDto;
import com.soitio.inventory.common.PageDto;

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
