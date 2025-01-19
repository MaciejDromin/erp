package com.soitio.inventory.contractor.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
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
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.contractor.application.ContractorRepository;
import com.soitio.inventory.contractor.domain.dto.ContractorCreationDto;
import com.soitio.inventory.contractor.domain.dto.ContractorDto;

import java.util.Set;

@Path("/contractors")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorRepository contractorRepository;

    @GET
    public PageDto<ContractorDto> getContractors(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return contractorRepository.getContractors(uriInfo, orgId);
    }

    @GET
    @Path("/{contractorId}")
    public ContractorDto getContractor(@PathParam("contractorId") String contractorId, @HeaderParam("X-OrgId") String orgId) {
        return contractorRepository.getContractor(contractorId, orgId);
    }

    @POST
    public void createContractor(ContractorCreationDto contractorCreation, @HeaderParam("X-OrgId") String orgId) {
        contractorRepository.create(contractorCreation, orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return contractorRepository.delete(Dependent.INVENTORY_CONTRACTOR, ids, orgId);
    }

    @PATCH
    @Path("/{contractorId}")
    public void updateSingleCategory(@PathParam("contractorId") String contractorId,
                                     JsonNode node,
                                     @HeaderParam("X-OrgId") String orgId) {
        contractorRepository.update(Dependent.INVENTORY_CONTRACTOR, contractorId, node, orgId);
    }

}
