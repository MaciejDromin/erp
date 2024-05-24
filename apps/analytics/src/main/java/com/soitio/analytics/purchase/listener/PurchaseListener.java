package com.soitio.analytics.purchase.listener;

import com.soitio.analytics.purchase.PurchaseAnalizer;
import com.soitio.analytics.purchase.dto.PurchaseToAnalyzeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseListener {

    private final PurchaseAnalizer purchaseAnalizer;

    @RabbitListener(queues = "purchase_analytics_queue")
    public void processMessage(List<PurchaseToAnalyzeDto> purchases) {
        purchaseAnalizer.process(purchases);
    }

}
