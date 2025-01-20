package com.soitio.analytics.purchase;

import static com.soitio.analytics.common.utils.FunctionUtils.calculateAndSet;

import com.soitio.analytics.common.domain.OrgWrapper;
import com.soitio.analytics.common.messaging.RabbitPublisher;
import com.soitio.analytics.purchase.dto.MonthlyPurchaseStatisticsDto;
import com.soitio.analytics.purchase.dto.PurchaseToAnalyzeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseAnalyzer {

    private final RabbitPublisher rabbitPublisher;
    private final MonthlyAnalyzerService monthlyAnalyzerService;
    private final SinglePurchaseAnalyzerService singlePurchaseAnalyzerService;

    public void process(OrgWrapper<List<PurchaseToAnalyzeDto>> purchases) {
        MonthlyPurchaseStatisticsDto statistics = calculateStatistics(purchases.data());
        rabbitPublisher.publish("purchase_statistics_queue", new OrgWrapper<>(purchases.orgId(), statistics));
    }

    private MonthlyPurchaseStatisticsDto calculateStatistics(List<PurchaseToAnalyzeDto> purchases) {
        MonthlyPurchaseStatisticsDto stats = monthlyAnalyzerService.calculateMonthlyStats(purchases);
        calculateAndSet(stats::setPurchases, () -> singlePurchaseAnalyzerService.calculatePurchases(purchases));
        return stats;
    }

}
