package com.soitio.finances.currency.client;

import com.soitio.finances.common.configuration.FeignConfig;
import com.soitio.finances.currency.domain.dto.CurrencyResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sync-client",
        url = "http://api.nbp.pl/api/exchangerates",
        configuration = FeignConfig.class)
public interface CurrencySyncClient {

    @GetMapping(value = "/tables/{table}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<CurrencyResponseDto> getTodaysCurrencyTable(@PathVariable("table") String table);

}
