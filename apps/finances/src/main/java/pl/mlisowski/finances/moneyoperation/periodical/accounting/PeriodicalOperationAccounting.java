package pl.mlisowski.finances.moneyoperation.periodical.accounting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PeriodicalOperationAccounting {

    private final PeriodicalOperationAccountingService accountingService;

    @Scheduled(cron = "0 0 11 11 * *")
    public void accountOperationsForCurrentMonth() {
        log.info("Started accounting periodical operations");
        accountingService.accountOperationsForCurrentMonth();
    }

}
