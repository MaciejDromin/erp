package pl.mlisowski.selfregister.domain;

import jakarta.inject.Singleton;

@Singleton
public class ServiceContext {

    private String serviceId = null;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
