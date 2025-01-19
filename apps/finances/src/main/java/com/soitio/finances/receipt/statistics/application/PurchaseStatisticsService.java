package com.soitio.finances.receipt.statistics.application;

import com.soitio.finances.receipt.application.PurchaseService;
import com.soitio.finances.receipt.domain.dto.OrgWrapper;
import com.soitio.finances.receipt.statistics.application.port.PurchaseStatisticsRepository;
import com.soitio.finances.receipt.statistics.domain.MonthlyStatistics;
import com.soitio.finances.receipt.statistics.domain.PurchaseStatistics;
import com.soitio.finances.receipt.statistics.domain.dto.MonthlyPurchaseStatisticsDto;
import com.soitio.finances.receipt.statistics.domain.dto.PurchaseStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseStatisticsService {

    private final PurchaseService purchaseService;
    private final PurchaseStatisticsRepository purchaseStatisticsRepository;
    private final MonthlyStatisticsService monthlyStatisticsService;

    public void saveMonthlyStatistics(OrgWrapper<MonthlyPurchaseStatisticsDto> content) {
        MonthlyStatistics monthlyStatistics = monthlyStatisticsService.createMonthly(content);
        purchaseStatisticsRepository.saveAll(content.data().getPurchases().stream()
                .map(p -> {
                    var mapped = mapToPurchaseEntity(p, content.orgId());
                    mapped.setMonthlyStatistics(monthlyStatistics);
                    return mapped;
                })
                .toList());
    }

    private PurchaseStatistics mapToPurchaseEntity(PurchaseStatisticsDto p, String orgId) {
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
                .orgId(orgId)
                .build();
    }

}
