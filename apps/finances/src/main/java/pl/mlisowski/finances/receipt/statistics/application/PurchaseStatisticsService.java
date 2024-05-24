package pl.mlisowski.finances.receipt.statistics.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.receipt.application.PurchaseService;
import pl.mlisowski.finances.receipt.statistics.application.port.PurchaseStatisticsRepository;
import pl.mlisowski.finances.receipt.statistics.domain.MonthlyStatistics;
import pl.mlisowski.finances.receipt.statistics.domain.PurchaseStatistics;
import pl.mlisowski.finances.receipt.statistics.domain.dto.MonthlyPurchaseStatisticsDto;
import pl.mlisowski.finances.receipt.statistics.domain.dto.PurchaseStatisticsDto;

@Service
@RequiredArgsConstructor
public class PurchaseStatisticsService {

    private final PurchaseService purchaseService;
    private final PurchaseStatisticsRepository purchaseStatisticsRepository;
    private final MonthlyStatisticsService monthlyStatisticsService;

    public void saveMonthlyStatistics(MonthlyPurchaseStatisticsDto content) {
        MonthlyStatistics monthlyStatistics = monthlyStatisticsService.createMonthly(content);
        purchaseStatisticsRepository.saveAll(content.getPurchases().stream()
                .map(p -> {
                    var mapped = mapToPurchaseEntity(p);
                    mapped.setMonthlyStatistics(monthlyStatistics);
                    return mapped;
                })
                .toList());
    }

    private PurchaseStatistics mapToPurchaseEntity(PurchaseStatisticsDto p) {
        return PurchaseStatistics.builder()
                .purchase(purchaseService.getById(p.getId()))
                .min(p.getMin())
                .max(p.getMax())
                .mean(p.getMean())
                .median(p.getMedian())
                .variation(p.getVariation())
                .standardDeviation(p.getStandardDeviation())
                .total(p.getTotal())
                .totalItems(p.getTotalItems())
                .build();
    }

}
