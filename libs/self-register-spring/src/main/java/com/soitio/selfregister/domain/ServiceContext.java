package com.soitio.selfregister.domain;

import org.springframework.stereotype.Component;

@Component
public class ServiceContext {

    private String serviceId = null;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
