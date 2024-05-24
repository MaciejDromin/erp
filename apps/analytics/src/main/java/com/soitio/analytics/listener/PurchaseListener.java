package com.soitio.analytics.listener;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@ApplicationScoped
public class PurchaseListener {

    @Incoming("purchase_analytics_queue")
    public void processPurchases(String o) {
        log.info("ML --- {}", o);
    }

}
