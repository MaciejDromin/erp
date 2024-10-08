package com.soitio.finances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackages = {
    "com.soitio.finances",
    "com.soitio.selfregister"
})
@EnableScheduling
@ComponentScan(basePackages = {
    "com.soitio.finances",
    "com.soitio.selfregister"
})
public class FinancesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancesApplication.class, args);
    }

}
