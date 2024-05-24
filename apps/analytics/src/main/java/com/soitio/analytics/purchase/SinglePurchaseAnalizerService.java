package com.soitio.analytics.purchase;

import static com.soitio.analytics.common.utils.FunctionUtils.calculateAndSet;

import com.soitio.analytics.purchase.dto.PurchaseItemToAnalyzeDto;
import com.soitio.analytics.purchase.dto.PurchaseStatisticsDto;
import com.soitio.analytics.purchase.dto.PurchaseToAnalyzeDto;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SinglePurchaseAnalizerService {

    public List<PurchaseStatisticsDto> calculatePurchases(List<PurchaseToAnalyzeDto> purchases) {
        return purchases.stream()
                .map(p -> {
                    var purchase = calculateSinglePurchase(p.getItems());
                    purchase.setId(p.getId());
                    return purchase;
                })
                .toList();
    }

    private PurchaseStatisticsDto calculateSinglePurchase(List<PurchaseItemToAnalyzeDto> purchases) {
        PurchaseStatisticsDto stats = new PurchaseStatisticsDto();
        calculateAndSet(stats::setMin, () -> this.calculateMin(purchases));
        calculateAndSet(stats::setMax, () -> this.calculateMax(purchases));
        calculateAndSet(stats::setMean, () -> this.calculateMean(purchases));
        calculateAndSet(stats::setMedian, () -> this.calculateMedian(purchases));
        calculateAndSet(stats::setVariation, () -> this.calculateVariation(purchases));
        calculateAndSet(stats::setStandardDeviation, () -> this.calculateStandardDeviation(purchases));
        calculateAndSet(stats::setTotal, () -> this.calculateTotal(purchases));
        calculateAndSet(stats::setTotalItems, () -> this.calculateTotalItems(purchases));
        return stats;
    }

    private BigDecimal calculateMin(List<PurchaseItemToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseItemToAnalyzeDto::getPrice)
                .min(BigDecimal::compareTo)
                .orElseThrow(); // TODO: Throw here a dedicated exception
    }

    private BigDecimal calculateMax(List<PurchaseItemToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseItemToAnalyzeDto::getPrice)
                .max(BigDecimal::compareTo)
                .orElseThrow(); // TODO: Throw here a dedicated exception
    }

    private BigDecimal calculateMean(List<PurchaseItemToAnalyzeDto> purchase) {
        return this.calculateTotal(purchase).divide(BigDecimal.valueOf(
                this.calculateTotalItems(purchase)), RoundingMode.DOWN);
    }

    private BigDecimal calculateMedian(List<PurchaseItemToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseItemToAnalyzeDto::getPrice)
                .sorted()
                .collect(Collectors.collectingAndThen(Collectors.toList(), amounts -> {
                    int noitems = amounts.size();
                    return noitems % 2 == 0 ? (amounts.get(noitems / 2 - 1).add(amounts.get(noitems / 2)))
                            .divide(BigDecimal.valueOf(2), RoundingMode.DOWN)
                            : amounts.get(noitems / 2);
                }));
    }

    private BigDecimal calculateVariation(List<PurchaseItemToAnalyzeDto> purchase) {
        BigDecimal mean = this.calculateMean(purchase);
        return purchase.stream()
                .map(PurchaseItemToAnalyzeDto::getPrice)
                .map(amount -> amount.subtract(mean).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add).divide(mean, RoundingMode.DOWN);
    }

    private BigDecimal calculateStandardDeviation(List<PurchaseItemToAnalyzeDto> purchase) {
        return this.calculateVariation(purchase).sqrt(new MathContext(10));
    }

    private BigDecimal calculateTotal(List<PurchaseItemToAnalyzeDto> purchase) {
        return purchase.stream()
                .map(PurchaseItemToAnalyzeDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calculateTotalItems(List<PurchaseItemToAnalyzeDto> purchase) {
        return purchase.size();
    }

}
