package com.soitio.analytics.purchase;

import static com.soitio.analytics.common.utils.FunctionUtils.calculateAndSet;

import com.soitio.analytics.common.messaging.RabbitPublisher;
import com.soitio.analytics.purchase.dto.MonthlyPurchaseStatisticsDto;
import com.soitio.analytics.purchase.dto.PurchaseToAnalyzeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseAnalizer {

    private final RabbitPublisher rabbitPublisher;
    private final MonthlyAnalizerService monthlyAnalizerService;
    private final SinglePurchaseAnalizerService singlePurchaseAnalizerService;

    public void process(List<PurchaseToAnalyzeDto> purchases) {
        MonthlyPurchaseStatisticsDto statistics = calculateStatistics(purchases);
        rabbitPublisher.publish("purchase_statistics_queue", statistics);
    }

    private MonthlyPurchaseStatisticsDto calculateStatistics(List<PurchaseToAnalyzeDto> purchases) {
        MonthlyPurchaseStatisticsDto stats = monthlyAnalizerService.calculateMonthlyStats(purchases);
        calculateAndSet(stats::setPurchases, () -> singlePurchaseAnalizerService.calculatePurchases(purchases));
        return stats;
    }

}
