package com.soitio.api.gateway;

import com.soitio.api.gateway.application.ConfigStore;
import com.soitio.api.gateway.domain.ConfigResource;
import com.soitio.api.gateway.domain.ConfigResourceDto;
import com.soitio.api.gateway.domain.ValueWrapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ConfigStoreService {

    private final ConfigStore configStore;

    public ConfigStoreService(ConfigStore configStore) {
        this.configStore = configStore;
    }

    public Uni<Boolean> updateOrCreate(String key, Object value) {
        return configStore.find("key", key)
                .firstResult()
                .onItem()
                .transformToUni(i -> {
                    if (i == null) {
                        return configStore.persist(ConfigResource.builder()
                                .key(key)
                                .value(new ValueWrapper(value))
                                .build());
                    }
                    i.setValue(new ValueWrapper(value));
                    return configStore.update(i);
                })
                .onItem()
                .transform(i -> Boolean.TRUE)
                .onFailure()
                .recoverWithItem(Boolean.FALSE);
    }

    public Uni<ConfigResourceDto> getCurrentVal(String key) {
        return configStore.find("key", key).firstResult()
                .onItem()
                .transform(cr -> new ConfigResourceDto(cr.getId(), cr.getKey(), cr.getValue().value()))
                .onFailure()
                .transform(NotFoundException::new);
    }

}
