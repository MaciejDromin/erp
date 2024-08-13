package com.soitio.inventory.maintenance.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.inventory.common.PageDto;
import com.soitio.inventory.maintenance.domain.MaintenanceRecord;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceCreationDto;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceForListDto;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceRepository implements PanacheMongoRepository<MaintenanceRecord> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

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

    private MaintenanceRecord from(MaintenanceCreationDto maintenanceCreation) {
        return MaintenanceRecord.builder()
                .date(maintenanceCreation.getDate())
                .odometer(maintenanceCreation.getOdometer())
                .parts(maintenanceCreation.getParts())
                .contractorId(maintenanceCreation.getContractorId())
                .build();
    }

}
