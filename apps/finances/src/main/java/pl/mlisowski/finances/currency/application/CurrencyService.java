package pl.mlisowski.finances.currency.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.currency.application.port.CurrencyRepository;
import pl.mlisowski.finances.currency.domain.Currency;
import pl.mlisowski.finances.currency.domain.CurrencyRateProj;
import pl.mlisowski.finances.currency.domain.dto.CurrencyEntryDto;
import pl.mlisowski.finances.currency.domain.dto.CurrencyResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Page<CurrencyEntryDto> getPage(Pageable pageable) {
        var pageReq = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "effectiveDate");
        return currencyRepository.findAll(pageReq).map(this::from);
    }

    public void parseAndSave(Optional<CurrencyResponseDto> response) {
        if (response.isEmpty()) {
            log.debug("Empty currencies response");
            return;
        }
        var table = response.get();
        var effectiveDate = table.getEffectiveDate();
        var parsedData = table.getRates()
                .parallelStream()
                .map(currency -> this.createFrom(currency, effectiveDate))
                .toList();
        currencyRepository.saveAll(parsedData);
    }

    private Currency createFrom(CurrencyEntryDto entry, LocalDate effectiveDate) {
        return Currency.builder()
                .currencyName(entry.getCurrency())
                .code(entry.getCode())
                .rate(entry.getMid())
                .effectiveDate(effectiveDate)
                .build();
    }

    private CurrencyEntryDto from(Currency currency) {
        return CurrencyEntryDto.builder()
                .currency(currency.getCurrencyName())
                .code(currency.getCode())
                .mid(currency.getRate())
                .effectiveDate(currency.getEffectiveDate())
                .build();
    }

    public Map<String, BigDecimal> getRatesForCurrencies(List<String> currencies) {
        List<CurrencyRateProj> currencyList = currencyRepository.findAllLatestByCode(currencies);
        return currencyList.stream()
                .collect(Collectors.toMap(CurrencyRateProj::getCode, CurrencyRateProj::getRate));
    }
}
