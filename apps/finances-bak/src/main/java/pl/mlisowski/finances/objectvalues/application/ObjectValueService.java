package pl.mlisowski.finances.objectvalues.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.currency.application.CurrencyService;
import pl.mlisowski.finances.objectvalues.application.port.ObjectValueRepository;
import pl.mlisowski.finances.objectvalues.domain.ObjectValue;
import pl.mlisowski.finances.objectvalues.domain.dto.ObjectValueCreationDto;
import pl.mlisowski.finances.objectvalues.domain.dto.ObjectValueDto;
import pl.mlisowski.finances.objectvalues.domain.dto.TotalObjectsValueDto;
import pl.mlisowski.finances.objectvalues.domain.proj.TotalValueProjection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectValueService {
    private final ObjectValueRepository objectValueRepository;
    private final CurrencyService currencyService;

    public void create(ObjectValueCreationDto creation) {
        objectValueRepository.save(createObject(creation));
    }

    private ObjectValue createObject(ObjectValueCreationDto creation) {
        return ObjectValue.builder()
                .objectId(creation.getObjectId())
                .amount(new BigDecimal(creation.getAmount()))
                .currency(creation.getCurrencyCode())
                .build();
    }

    public Page<ObjectValueDto> getPage(Pageable pageable) {
        return objectValueRepository.findAll(pageable).map(this::from);
    }

    private ObjectValueDto from(ObjectValue objectValue) {
        var amount = objectValue.getAmount();
        return ObjectValueDto.builder()
                .uuid(objectValue.getUuid())
                .objectId(objectValue.getObjectId())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .build();
    }

    public TotalObjectsValueDto totalValue() {
        List<TotalValueProjection> projections = objectValueRepository.countTotalValue();
        List<String> currencies = projections.stream()
                .map(TotalValueProjection::getCurrency)
                .filter(curr -> !curr.equalsIgnoreCase("PLN"))
                .toList();
        Map<String, BigDecimal> ratePerCurrencyCodeMap = currencyService.getRatesForCurrencies(currencies);
        Money totalAmount = Money.of(CurrencyUnit.of("PLN"), BigDecimal.ZERO);
        int objectsCount = 0;
        for (TotalValueProjection proj : projections) {
            var curr = proj.getCurrency();
            if (curr.equalsIgnoreCase("PLN")) {
                totalAmount = totalAmount.plus(Money.of(CurrencyUnit.of(curr), proj.getTotalAmount(), RoundingMode.DOWN));
            } else {
                totalAmount = totalAmount.plus(Money.of(CurrencyUnit.of("PLN"), proj.getTotalAmount().multiply(ratePerCurrencyCodeMap.get(curr)), RoundingMode.DOWN));
            }
            objectsCount += proj.getTotalCount();
        }
        return TotalObjectsValueDto.builder()
                .totalAmount(AmountDto.of(totalAmount.getAmount(), totalAmount.getCurrencyUnit().getCode()))
                .objectsCount(objectsCount)
                .build();
    }
}
