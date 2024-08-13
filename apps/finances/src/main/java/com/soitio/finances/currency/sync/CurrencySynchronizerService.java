package com.soitio.finances.currency.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.soitio.finances.currency.application.CurrencyService;
import com.soitio.finances.currency.client.CurrencySyncClient;

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
