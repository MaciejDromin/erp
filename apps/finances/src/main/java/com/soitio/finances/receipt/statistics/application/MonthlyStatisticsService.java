package com.soitio.finances.receipt.statistics.application;

import com.soitio.finances.receipt.statistics.application.port.MonthlyStatisticsRepository;
import com.soitio.finances.receipt.statistics.domain.MonthlyStatistics;
import com.soitio.finances.receipt.statistics.domain.dto.MonthlyPurchaseStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyStatisticsService {

    private final MonthlyStatisticsRepository monthlyStatisticsRepository;

    public MonthlyStatistics createMonthly(MonthlyPurchaseStatisticsDto content) {
        return monthlyStatisticsRepository.save(mapToMonthlyStatistics(content));
    }

    private MonthlyStatistics mapToMonthlyStatistics(MonthlyPurchaseStatisticsDto content) {
        return MonthlyStatistics.builder()
                .min(content.getMin())
                .max(content.getMax())
                .mean(content.getMean())
                .median(content.getMedian())
                .variation(content.getVariation())
                .standardDeviation(content.getStandardDeviation())
                .total(content.getTotal())
                .totalPurchases(content.getTotalPurchases())
                .totalItems(content.getTotalItems())
                .averageItemPerPurchase(content.getAverageItemPerPurchase())
                .month(content.getMonth())
                .year(content.getDate().getYear())
                .date(content.getDate())
                .build();
    }
}
