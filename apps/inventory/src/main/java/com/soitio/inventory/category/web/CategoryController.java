package com.soitio.inventory.category.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.inventory.category.application.CategoryRepository;

import java.util.Set;

@Path("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GET
    public PageDto<CategoryDto> getAllCategories(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return categoryRepository.findAll(uriInfo, orgId);
    }

    @GET
    @Path("/{categoryId}")
    public CategoryDto getSingleCategory(@PathParam("categoryId") String categoryId, @HeaderParam("X-OrgId") String orgId) {
        return categoryRepository.findOne(categoryId, orgId);
    }

    @POST
    public void addCategory(CategoryDto category, @HeaderParam("X-OrgId") String orgId) {
        categoryRepository.create(category, orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return categoryRepository.delete(Dependent.INVENTORY_CATEGORY, ids, orgId);
    }

    @PATCH
    @Path("/{categoryId}")
    public void updateSingleCategory(@PathParam("categoryId") String categoryId, JsonNode node, @HeaderParam("X-OrgId") String orgId) {
        categoryRepository.update(Dependent.INVENTORY_CATEGORY, categoryId, node, orgId);
    }

}
