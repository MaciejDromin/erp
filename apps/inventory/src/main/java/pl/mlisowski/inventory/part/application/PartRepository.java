package pl.mlisowski.inventory.part.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.part.domain.Part;
import pl.mlisowski.inventory.part.domain.dto.PartCreationDto;
import pl.mlisowski.inventory.part.domain.dto.PartDto;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class PartRepository implements PanacheMongoRepository<Part> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<PartDto> getParts(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Part> parts;
        if (objectIdsString == null) parts = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) parts = findAll();
            else {
                parts = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var propertyList = parts.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), parts.pageCount());
    }

    public PanacheQuery<Part> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    public void create(PartCreationDto partCreation) {
        persist(from(partCreation));
    }

    private Part from(PartCreationDto part) {
        return Part.builder()
                .name(part.getName())
                .partNumber(part.getPartNumber())
                .manufacturerId(part.getManufacturerId())
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
}
