package com.soitio.dashboard.widget.definition.application;

import com.soitio.dashboard.widget.definition.domain.WidgetDefinitionEntity;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionDto;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionNameDto;
import com.soitio.widgets.common.domain.WidgetDefinition;
import com.soitio.widgets.common.domain.WidgetDomain;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@ApplicationScoped
public class WidgetDefinitionRepository implements PanacheMongoRepository<WidgetDefinitionEntity> {

    public void createWidgets(List<WidgetDefinition> widgetDefinitions) {
        List<WidgetDefinitionEntity> toSave = widgetDefinitions.stream()
                .filter(wd -> !widgetExists(wd.getUniqueCode()))
                .map(this::mapToEntity)
                .toList();

        Map<String, WidgetDefinition> codeWidgetDefinitionMap = widgetDefinitions.stream()
                .collect(Collectors.toMap(WidgetDefinition::getUniqueCode, wd -> wd));

        List<WidgetDefinitionEntity> toUpdate = find("uniqueCode in ?1", codeWidgetDefinitionMap.keySet()).stream()
                .filter(entity -> !codeWidgetDefinitionMap.get(entity.getUniqueCode()).getVersion()
                        .equals(entity.getVersion()))
                .toList();

        toUpdate
                .forEach(wd -> updateWidgetDefinition(wd, codeWidgetDefinitionMap.get(wd.getUniqueCode())));

        update(toUpdate);
        persist(toSave);
    }

    private void updateWidgetDefinition(WidgetDefinitionEntity wd, WidgetDefinition widgetDefinition) {
        wd.setName(widgetDefinition.getName());
        wd.setDatasource(widgetDefinition.getDatasource());
        wd.setAvailableFilters(widgetDefinition.getAvailableFilters());
        wd.setVersion(widgetDefinition.getVersion());
    }

    private boolean widgetExists(String uniqueCode) {
        return find("uniqueCode = ?1", uniqueCode).count() > 0;
    }

    private WidgetDefinitionEntity mapToEntity(WidgetDefinition widgetDefinition) {
        return WidgetDefinitionEntity.builder()
                .name(widgetDefinition.getName())
                .datasource(widgetDefinition.getDatasource())
                .availableFilters(widgetDefinition.getAvailableFilters())
                .widgetDomain(widgetDefinition.getWidgetDomain())
                .version(widgetDefinition.getVersion())
                .uniqueCode(widgetDefinition.getUniqueCode())
                .build();
    }

    public List<WidgetDefinitionNameDto> getWidgetDefinitionNames(WidgetDomain widgetDomain) {
        return find("widgetDomain = ?1", widgetDomain)
                .project(WidgetDefinitionNameDto.class)
                .list();
    }

    public WidgetDefinitionDto getById(String widgetDefinitionId) {
        return to(findById(new ObjectId(widgetDefinitionId)));
    }

    private WidgetDefinitionDto to(WidgetDefinitionEntity entity) {
        return WidgetDefinitionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .datasource(entity.getDatasource())
                .availableFilters(entity.getAvailableFilters())
                .widgetDomain(entity.getWidgetDomain())
                .version(entity.getVersion())
                .uniqueCode(entity.getUniqueCode())
                .build();
    }
}
