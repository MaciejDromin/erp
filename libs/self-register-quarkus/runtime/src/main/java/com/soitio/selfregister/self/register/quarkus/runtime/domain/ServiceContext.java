package com.soitio.selfregister.self.register.quarkus.runtime.domain;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceContext {

    private String serviceId = null;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
