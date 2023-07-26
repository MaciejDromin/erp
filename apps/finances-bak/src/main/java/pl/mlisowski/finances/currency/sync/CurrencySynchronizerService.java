package pl.mlisowski.finances.currency.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.currency.application.CurrencyService;
import pl.mlisowski.finances.currency.client.CurrencySyncClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencySynchronizerService {

    private static final String TABLE_A = "A";

    private final CurrencySyncClient client;
    private final CurrencyService currencyService;

    public void synchronizeCurrencyTable() {
        currencyService.parseAndSave(client.getTodaysCurrencyTable(TABLE_A).stream().findFirst());
    }

}
