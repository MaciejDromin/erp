package pl.mlisowski.finances.currency.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mlisowski.finances.common.configuration.FeignConfig;
import pl.mlisowski.finances.currency.domain.dto.CurrencyResponseDto;

@FeignClient(name = "sync-client",
        url = "http://api.nbp.pl/api/exchangerates",
        configuration = FeignConfig.class)
public interface CurrencySyncClient {

    @GetMapping(value = "/tables/{table}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<CurrencyResponseDto> getTodaysCurrencyTable(@PathVariable("table") String table);

}
