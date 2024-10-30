package com.soitio.inventory.item.application;

import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckResult;
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
public class InventoryItemRepository extends AbstractDependencyCheckRepo<InventoryItem, InventoryItemDto> implements DependencyCheckService {

    private static final String SERVICE_NAME = "InventoryItem";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private final CategoryRepository categoryRepository;

    public InventoryItemRepository(DependencyCheckRequester dependencyCheckRequester,
                                   CategoryRepository categoryRepository) {
        super(dependencyCheckRequester);
        this.categoryRepository = categoryRepository;
    }

    public PageDto<InventoryItemDto> listAllItems(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<InventoryItem> items;
        if (objectIdsString == null) items = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) items = findAll();
            else {
                items = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var requestedSize = params.getFirst("size");
        var size = requestedSize == null ? DEFAULT_PAGE_SIZE : Integer.parseInt(requestedSize);
        var itemList = items.page(pageNum, size).list();
        return PageDto.of(itemList.stream()
                .map(this::convert)
                .toList(), items.pageCount());
    }

    private InventoryItemDto convert(InventoryItem item) {
        return InventoryItemDto.builder()
                .id(item.getId().toString())
                .name(item.getName())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .categories(categoryRepository.findAllByIdsIn(item.getCategories()).stream()
                        .map(categoryRepository::convert)
                        .collect(Collectors.toSet()))
                .build();
    }

    public void create(ItemCreationDto itemCreation) {
        var item = convert(itemCreation);
        item.setCategories(itemCreation.getCategoryIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        persist(item);
    }

    private InventoryItem convert(ItemCreationDto itemCreation) {
        return InventoryItem.builder()
                .name(itemCreation.getName())
                .unit(itemCreation.getUnit())
                .quantity(itemCreation.getQuantity())
                .build();
    }

    public Map<String, String> findAllItemNamesByIds(List<String> objectIds) {
        Map<String, String> ret = new HashMap<>();
        var items = findAllByIdsIn(objectIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    public Set<InventoryItem> findAllByIdsIn(Set<ObjectId> itemIds) {
        return new HashSet<>(list("_id in ?1", itemIds));
    }

    public PanacheQuery<InventoryItem> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    public Map<String, Integer> findCountByObjectsIds(List<String> objectIds) {
        var objects = findAllByIdsIn(objectIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        return objects.stream()
                .collect(Collectors.toMap(item -> item.getId().toString(), InventoryItem::getQuantity));
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<String> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<String> set) {
        var objectIds = set.stream()
                .map(ObjectId::new).collect(Collectors.toSet());
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
    public void updateOne(String id, InventoryItemDto object) {
        // TODO: Implement
    }
}
