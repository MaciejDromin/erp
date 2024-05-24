package com.soitio.analytics.purchase;

import static com.soitio.analytics.common.utils.FunctionUtils.calculateAndSet;

import com.soitio.analytics.common.messaging.RabbitPublisher;
import com.soitio.analytics.purchase.dto.MonthlyPurchaseStatistics;
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
        MonthlyPurchaseStatistics statistics = calculateStatistics(purchases);
        rabbitPublisher.publish("purchase_statistics_queue", statistics);
    }

    private MonthlyPurchaseStatistics calculateStatistics(List<PurchaseToAnalyzeDto> purchases) {
        MonthlyPurchaseStatistics stats = monthlyAnalizerService.calculateMonthlyStats(purchases);
        calculateAndSet(stats::setPurchases, () -> singlePurchaseAnalizerService.calculatePurchases(purchases));
        return stats;
    }

}
