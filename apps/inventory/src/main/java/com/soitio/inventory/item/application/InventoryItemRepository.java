package com.soitio.inventory.item.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.inventory.ItemUnit;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import com.soitio.inventory.category.application.CategoryRepository;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.item.InventoryItemDto;
import com.soitio.inventory.item.domain.InventoryItem;
import com.soitio.inventory.item.domain.dto.ItemCreationDto;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class InventoryItemRepository extends AbstractDependencyCheckRepo<InventoryItem> implements DependencyCheckService {

    private static final String SERVICE_NAME = "InventoryItem";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private final CategoryRepository categoryRepository;

    public InventoryItemRepository(ObjectMapper mapper,
                                   DependencyCheckRequester dependencyCheckRequester,
                                   CategoryRepository categoryRepository) {
        super(mapper, dependencyCheckRequester);
        this.categoryRepository = categoryRepository;
    }

    public PageDto<InventoryItemDto> listAllItems(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<InventoryItem> items;
        if (objectIdsString == null) items = findAllByOrgId(orgId);
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) items = findAllByOrgId(orgId);
            else {
                items = findAllByIdsNotInAndOrgId(objectIds, orgId);
            }
        }
        var requestedSize = params.getFirst("size");
        var size = requestedSize == null ? DEFAULT_PAGE_SIZE : Integer.parseInt(requestedSize);
        var itemList = items.page(pageNum, size).list();
        return PageDto.of(itemList.stream()
                .map(i -> convert(i, orgId))
                .toList(), items.pageCount());
    }

    public void create(ItemCreationDto itemCreation, String orgId) {
        var item = convert(itemCreation, orgId);
        item.setCategories(itemCreation.getCategoryIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        persist(item);
    }

    private InventoryItemDto convert(InventoryItem item, String orgId) {
        return InventoryItemDto.builder()
                .id(item.getId().toString())
                .name(item.getName())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .categories(categoryRepository.findAllByIdsInAndOrgId(item.getCategories(), orgId).stream()
                        .map(categoryRepository::convert)
                        .collect(Collectors.toSet()))
                .build();
    }

    private InventoryItem convert(ItemCreationDto itemCreation, String orgId) {
        return InventoryItem.builder()
                .name(itemCreation.getName())
                .unit(itemCreation.getUnit())
                .quantity(itemCreation.getQuantity())
                .orgId(orgId)
                .build();
    }

    public Map<String, String> findAllItemNamesByIds(List<String> objectIds, String orgId) {
        Map<String, String> ret = new HashMap<>();
        var items = listAllByIdsInAndOrgId(objectIds, orgId);
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    public Map<String, Integer> findCountByObjectsIds(List<String> objectIds, String orgId) {
        var objects = findAllByIdsInAndOrgId(objectIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()), orgId);
        return objects.stream()
                .collect(Collectors.toMap(item -> item.getId().toString(), InventoryItem::getQuantity));
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
        var allCategories = findAllByCategoryIdsIn(objectIds)
                .stream()
                .map(InventoryItem::getCategories)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return objectIds.stream()
                .filter(allCategories::contains)
                .map(oid -> new DependencyCheckResult(oid.toString(), true, "Category with id '%s' is in use".formatted(oid)))
                .collect(Collectors.toSet());
    }

    private Set<InventoryItem> findAllByCategoryIdsIn(Set<ObjectId> categoryIds) {
        return new HashSet<>(list("categories in ?1", categoryIds));
    }

    @Override
    protected InventoryItem mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return InventoryItem.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .unit(ItemUnit.valueOf(fields.get("unit").getStrValue()))
                .quantity(fields.get("quantity").getIntValue())
                .categories(fields.get("categories").getListValue().stream()
                        .map(mp -> new ObjectId(mp.getStrValue()))
                        .collect(Collectors.toSet()))
                .build();
    }

    public InventoryItemDto getItem(String itemId, String orgId) {
        return convert(findByIdAndOrgId(itemId, orgId), orgId);
    }

}
