package com.soitio.inventory.contractor.web;

import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
    public PageDto<ContractorDto> getContractors(@Context UriInfo uriInfo) {
        return contractorRepository.getContractors(uriInfo);
    }

    @POST
    public void createContractor(ContractorCreationDto contractorCreation) {
        contractorRepository.create(contractorCreation);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return contractorRepository.delete(Dependent.INVENTORY_CONTRACTOR, ids);
    }

}
