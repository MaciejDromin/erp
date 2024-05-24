package pl.mlisowski.finances.receipt.statistics.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.mlisowski.finances.receipt.statistics.application.PurchaseStatisticsService;
import pl.mlisowski.finances.receipt.statistics.domain.dto.MonthlyPurchaseStatisticsDto;

@Component
@RequiredArgsConstructor
public class PurchaseStatisticsListener {

    private final PurchaseStatisticsService purchaseStatisticsService;

    @RabbitListener(queues = "purchase_statistics_queue")
    public void processMessage(MonthlyPurchaseStatisticsDto content) {
        purchaseStatisticsService.saveMonthlyStatistics(content);
    }

}
