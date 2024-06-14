package com.soitio.analytics.purchase;

import static com.soitio.analytics.common.utils.FunctionUtils.calculateAndSet;

import com.soitio.analytics.purchase.dto.MonthlyPurchaseStatisticsDto;
import com.soitio.analytics.purchase.dto.PurchaseToAnalyzeDto;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MonthlyAnalizerService {

    public MonthlyPurchaseStatisticsDto calculateMonthlyStats(List<PurchaseToAnalyzeDto> purchases) {
        MonthlyPurchaseStatisticsDto stats = new MonthlyPurchaseStatisticsDto();
        LocalDateTime date = this.getDate(purchases);
        stats.setDate(date);
        stats.setMonth(date.getMonth());
        calculateAndSet(stats::setMin, () -> this.calculateMin(purchases));
        calculateAndSet(stats::setMax, () -> this.calculateMax(purchases));
        calculateAndSet(stats::setMean, () -> this.calculateMean(purchases));
        calculateAndSet(stats::setMedian, () -> this.calculateMedian(purchases));
        calculateAndSet(stats::setVariation, () -> this.calculateVariation(purchases));
        calculateAndSet(stats::setStandardDeviation, () -> this.calculateStandardDeviation(purchases));
        calculateAndSet(stats::setTotal, () -> this.calculateTotal(purchases));
        calculateAndSet(stats::setTotalPurchases, () -> this.calculateTotalPurchases(purchases));
        calculateAndSet(stats::setTotalItems, () -> this.calculateTotalItems(purchases));
        calculateAndSet(stats::setAverageItemPerPurchase, () -> this.calculateAverageItemPerPurchase(purchases));
        return stats;
    }

    private LocalDateTime getDate(List<PurchaseToAnalyzeDto> purchases) {
        return purchases.stream()
                .map(PurchaseToAnalyzeDto::getDate)
                .findFirst() // TODO: Throw dedicated exception
                .orElseThrow();
    }

    private BigDecimal calculateMin(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getAmount)
                .min(BigDecimal::compareTo)
                .orElseThrow(); // TODO: Throw here a dedicated exception
    }

    private BigDecimal calculateMax(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getAmount)
                .max(BigDecimal::compareTo)
                .orElseThrow(); // TODO: Throw here a dedicated exception
    }

    private BigDecimal calculateMean(List<PurchaseToAnalyzeDto> purchase) {
        return this.calculateTotal(purchase).divide(BigDecimal.valueOf(
                this.calculateTotalPurchases(purchase)), RoundingMode.DOWN);
    }

    private BigDecimal calculateMedian(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getAmount)
                .sorted()
                .collect(Collectors.collectingAndThen(Collectors.toList(), amounts -> {
                    int noitems = amounts.size();
                    return noitems % 2 == 0 ? (amounts.get(noitems / 2 - 1).add(amounts.get(noitems / 2)))
                            .divide(BigDecimal.valueOf(2), RoundingMode.DOWN)
                            : amounts.get(noitems / 2);
                }));
    }

    private BigDecimal calculateVariation(List<PurchaseToAnalyzeDto> purchase) {
        BigDecimal mean = this.calculateMean(purchase);
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getAmount)
                .map(amount -> amount.subtract(mean).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add).divide(mean, RoundingMode.DOWN);
    }

    private BigDecimal calculateStandardDeviation(List<PurchaseToAnalyzeDto> purchase) {
        return this.calculateVariation(purchase).sqrt(new MathContext(10));
    }

    private BigDecimal calculateTotal(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calculateTotalPurchases(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.size();
    }

    private int calculateTotalItems(List<PurchaseToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseToAnalyzeDto::getItems)
                .mapToInt(List::size)
                .sum();
    }

    private double calculateAverageItemPerPurchase(List<PurchaseToAnalyzeDto> purchase) {
        return this.calculateTotalItems(purchase) * 1.0 / this.calculateTotalPurchases(purchase);
    }

}
