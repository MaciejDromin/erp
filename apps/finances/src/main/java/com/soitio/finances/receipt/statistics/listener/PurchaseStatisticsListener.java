package com.soitio.finances.receipt.statistics.listener;

import com.soitio.finances.receipt.statistics.application.PurchaseStatisticsService;
import com.soitio.finances.receipt.statistics.domain.dto.MonthlyPurchaseStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseStatisticsListener {

    private final PurchaseStatisticsService purchaseStatisticsService;

    @RabbitListener(queues = "purchase_statistics_queue")
    public void processMessage(MonthlyPurchaseStatisticsDto content) {
        purchaseStatisticsService.saveMonthlyStatistics(content);
    }

}
