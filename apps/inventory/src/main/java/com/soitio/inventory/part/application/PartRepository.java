package com.soitio.inventory.part.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.part.domain.Part;
import com.soitio.inventory.part.domain.dto.PartCreationDto;
import com.soitio.inventory.part.domain.dto.PartDto;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PartRepository extends AbstractDependencyCheckRepo<Part> implements DependencyCheckService {

    private static final String SERVICE_NAME = "Part";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PartRepository(ObjectMapper mapper,
                          DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    public PageDto<PartDto> getParts(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 0 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        var partIdsString = params.getFirst("partIds");
        PanacheQuery<Part> parts;
        if (objectIdsString != null) {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) parts = findAllByOrgId(orgId);
            else {
                parts = findAllByIdsNotInAndOrgId(objectIds, orgId);
            }
        } else if (partIdsString != null) {
            List<String> partIds = Arrays.asList(partIdsString.split(","));
            if (partIds.isEmpty()) parts = findAll();
            else parts = findAllByIdsInAndOrgId(partIds, orgId);
        } else parts = findAll();
        var size = params.getFirst("size");
        var sizeToFetch = size == null ? DEFAULT_PAGE_SIZE : Integer.parseInt(size);
        var partsList = parts.page(pageNum, sizeToFetch).list();
        return PageDto.of(partsList.stream()
                .map(this::to)
                .toList(), parts.pageCount());
    }

    public void create(PartCreationDto partCreation, String orgId) {
        persist(from(partCreation, orgId));
    }

    private Part from(PartCreationDto part, String orgId) {
        return Part.builder()
                .name(part.getName())
                .partNumber(part.getPartNumber())
                .manufacturerId(part.getManufacturerId())
                .orgId(orgId)
                .build();
    }

    private PartDto to(Part part) {
        return PartDto.builder()
                .id(part.getId())
                .name(part.getName())
                .partNumber(part.getPartNumber())
                .manufacturerId(part.getManufacturerId())
                .build();
    }

    public Set<Part> findAllByManufacturerIdsIn(Set<ObjectId> itemIds) {
        return new HashSet<>(list("manufacturerId in ?1", itemIds));
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<DependencyCheckContext> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<DependencyCheckContext> set) {
        return findAllByManufacturerIdsIn(set.stream()
                .map(DependencyCheckContext::id)
                .map(ObjectId::new)
                .collect(Collectors.toSet()))
                .stream()
                .map(p -> new DependencyCheckResult(p.getManufacturerId().toString(), true, "Contractor with id '%s' is in use!".formatted(p.getManufacturerId())))
                .collect(Collectors.toSet());
    }

    @Override
    protected Part mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return Part.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .partNumber(fields.get("partNumber").getStrValue())
                .manufacturerId(new ObjectId(fields.get("manufacturerId").getStrValue()))
                .build();
    }

    public PartDto getPart(String id, String orgId) {
        return to(findByIdAndOrgId(id, orgId));
    }
}
