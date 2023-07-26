package pl.mlisowski.finances.currency.debug;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mlisowski.finances.currency.sync.CurrencySynchronizerService;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class CurrencyDebugController {

    private final CurrencySynchronizerService synchronizerService;

    @GetMapping("/currency")
    public void requestSynchronization() {
        synchronizerService.synchronizeCurrencyTable();
    }

}
