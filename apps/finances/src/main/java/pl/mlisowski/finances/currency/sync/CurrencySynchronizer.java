package pl.mlisowski.finances.currency.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CurrencySynchronizer {

    private final CurrencySynchronizerService synchronizerService;

    @Scheduled(cron = "0 0 13 * * MON-FRI")
    public void scheduleCurrencySynchronizationTask() {
        log.info("Synchronizing currency table");
        synchronizerService.synchronizeCurrencyTable();
    }

}
