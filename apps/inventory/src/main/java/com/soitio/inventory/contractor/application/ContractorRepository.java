package com.soitio.inventory.contractor.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.contractor.contact.domain.ContactInformation;
import com.soitio.inventory.contractor.contact.domain.dto.ContactInformationDto;
import com.soitio.inventory.contractor.domain.Contractor;
import com.soitio.inventory.contractor.domain.dto.ContractorCreationDto;
import com.soitio.inventory.contractor.domain.dto.ContractorDto;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ContractorRepository extends AbstractDependencyCheckRepo<Contractor> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public ContractorRepository(ObjectMapper mapper,
                                DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    public PageDto<ContractorDto> getContractors(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Contractor> contractors;
        if (objectIdsString == null) contractors = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) contractors = findAll();
            else {
                contractors = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var propertyList = contractors.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), contractors.pageCount());
    }

    public PanacheQuery<Contractor> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    public void create(ContractorCreationDto contractorCreation) {
        persist(from(contractorCreation));
    }

    private Contractor from(ContractorCreationDto contractorCreation) {
        return Contractor.builder()
                .name(contractorCreation.getName())
                .contactInformation(fromContactInformation(contractorCreation.getContactInformation()))
                .build();
    }

    private ContactInformation fromContactInformation(ContactInformationDto contactInformation) {
        return ContactInformation.builder()
                .email(contactInformation.email())
                .phoneNumber(contactInformation.phoneNumber())
                .website(contactInformation.website())
                .build();
    }

    private ContractorDto to(Contractor contractor) {
        return ContractorDto.builder()
                .id(contractor.getId())
                .name(contractor.getName())
                .contactInformation(toContactInformation(contractor.getContactInformation()))
                .build();
    }

    private ContactInformationDto toContactInformation(ContactInformation contactInformation) {
        return new ContactInformationDto(contactInformation.getPhoneNumber(),
                contactInformation.getEmail(),
                contactInformation.getWebsite());
    }

    @Override
    protected Contractor mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        var contactInfo = fields.get("contactInformation").getObjectValue();
        return Contractor.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .contactInformation(ContactInformation.builder()
                        .phoneNumber(contactInfo.get("phoneNumber").getStrValue())
                        .email(contactInfo.get("email").getStrValue())
                        .website(contactInfo.get("website").getStrValue())
                        .build())
                .build();
    }

    public ContractorDto getContractor(String contractorId) {
        return to(findById(new ObjectId(contractorId)));
    }
}
