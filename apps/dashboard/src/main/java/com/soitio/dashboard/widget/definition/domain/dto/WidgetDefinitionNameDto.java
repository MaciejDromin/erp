package com.soitio.dashboard.widget.definition.domain.dto;

import com.soitio.dashboard.widget.definition.domain.WidgetDefinitionEntity;
import io.quarkus.mongodb.panache.common.ProjectionFor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ProjectionFor(WidgetDefinitionEntity.class)
public class WidgetDefinitionNameDto {

    private ObjectId id;
    private String name;

}
