package com.soitio.analytics.purchase.listener;

import com.soitio.analytics.common.domain.OrgWrapper;
import com.soitio.analytics.purchase.PurchaseAnalyzer;
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

    private final PurchaseAnalyzer purchaseAnalyzer;

    @RabbitListener(queues = "purchase_analytics_queue")
    public void processMessage(OrgWrapper<List<PurchaseToAnalyzeDto>> purchases) {
        purchaseAnalyzer.process(purchases);
    }

}
