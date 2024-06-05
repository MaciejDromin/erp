package com.soitio.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = {
    "com.soitio.analytics",
    "com.soitio.selfregister"
})
@ComponentScan(basePackages = {
    "com.soitio.analytics",
    "com.soitio.selfregister"
})
public class AnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

}
