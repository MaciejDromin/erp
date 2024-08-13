package com.soitio.planner.material.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.planner.common.dto.PageDto;
import com.soitio.planner.material.domain.Material;
import com.soitio.planner.material.domain.dto.MaterialCreationDto;
import com.soitio.planner.material.domain.dto.MaterialDto;
import java.time.LocalDateTime;

@ApplicationScoped
public class MaterialRepository implements PanacheMongoRepository<Material> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<MaterialDto> findAll(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var materials = findAll();
        var materialsList = materials.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(materialsList.stream()
                .map(this::convert)
                .toList(), materials.pageCount());
    }

    public MaterialDto convert(Material material) {
        return MaterialDto.builder()
                .id(material.getId().toString())
                .name(material.getName())
                .unit(material.getUnit())
                .unitAmount(AmountDto.of(material.getUnitAmountMoney().getAmount(), material.getCurrency()))
                .updatedTime(material.getUpdatedTime())
                .source(material.getSource())
                .build();
    }

    public void create(MaterialCreationDto creation) {
        persist(convert(creation));
    }

    private Material convert(MaterialCreationDto creation) {
        var time = LocalDateTime.now();
        return Material.builder()
                .name(creation.getName())
                .unit(creation.getUnit())
                .currency(creation.getUnitAmount().getCurrencyCode())
                .unitAmount(creation.getUnitAmount().getValue())
                .updatedTime(time)
                .source(creation.getSource())
                .build();
    }

    public Material getById(ObjectId materialId) {
        return findById(materialId);
    }

}
