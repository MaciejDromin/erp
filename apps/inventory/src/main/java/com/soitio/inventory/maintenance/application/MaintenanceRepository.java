package com.soitio.inventory.maintenance.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.utils.DateUtils;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceRecordDto;
import com.soitio.inventory.maintenance.domain.dto.PartQuantity;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.maintenance.domain.MaintenanceRecord;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceCreationDto;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceForListDto;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class MaintenanceRepository extends AbstractDependencyCheckRepo<MaintenanceRecord>  implements DependencyCheckService {

    private static final String SERVICE_NAME = "MaintenanceRecord";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public MaintenanceRepository(ObjectMapper mapper,
                                 DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    public void create(MaintenanceCreationDto maintenanceCreation) {
        persist(from(maintenanceCreation));
    }

    public PageDto<MaintenanceForListDto> getForList(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<MaintenanceRecord> maintenance;
        if (objectIdsString == null) maintenance = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) maintenance = findAll();
            else {
                maintenance = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var propertyList = maintenance.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), maintenance.pageCount());
    }

    public PanacheQuery<MaintenanceRecord> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    private MaintenanceForListDto to(MaintenanceRecord maintenanceRecord) {
        return MaintenanceForListDto.builder()
                .id(maintenanceRecord.getId())
                .date(maintenanceRecord.getDate())
                .odometer(maintenanceRecord.getOdometer())
                .build();
    }

    private MaintenanceRecordDto toDto(MaintenanceRecord maintenanceRecord) {
        return MaintenanceRecordDto.builder()
                .id(maintenanceRecord.getId())
                .date(maintenanceRecord.getDate())
                .odometer(maintenanceRecord.getOdometer())
                .parts(maintenanceRecord.getParts())
                .contractorId(maintenanceRecord.getContractorId())
                .build();
    }

    private MaintenanceRecord from(MaintenanceCreationDto maintenanceCreation) {
        return MaintenanceRecord.builder()
                .date(maintenanceCreation.getDate())
                .odometer(maintenanceCreation.getOdometer())
                .parts(maintenanceCreation.getParts())
                .contractorId(maintenanceCreation.getContractorId())
                .build();
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
        var objectIds = set.stream()
                .map(DependencyCheckContext::id)
                .map(ObjectId::new)
                .collect(Collectors.toSet());
        var allContractors = findAllByContractorIdIn(objectIds)
                .stream()
                .map(MaintenanceRecord::getParts)
                .flatMap(Collection::stream)
                .map(PartQuantity::id)
                .collect(Collectors.toSet());
        return objectIds.stream()
                .filter(allContractors::contains)
                .map(oid -> new DependencyCheckResult(oid.toString(), true, "Contractor with id '%s' is in use".formatted(oid)))
                .collect(Collectors.toSet());
    }

    private Set<MaintenanceRecord> findAllByContractorIdIn(Set<ObjectId> set) {
        return new HashSet<>(list("parts.id in ?1", set));
    }

    @Override
    protected MaintenanceRecord mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return MaintenanceRecord.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .date(DateUtils.localDateFromString(fields.get("date").getStrValue()))
                .odometer(fields.get("odometer").getIntValue())
                .contractorId(new ObjectId(fields.get("contractorId").getStrValue()))
                .parts(fields.get("parts").getListValue().stream()
                        .map(MergePatch::getObjectValue)
                        .map(f -> new PartQuantity(new ObjectId(f.get("id").getStrValue()),
                                f.get("quantity").getIntValue()))
                        .toList())
                .build();
    }

    public MaintenanceRecordDto getMaintenance(String id) {
        return toDto(findById(new ObjectId(id)));
    }
}
