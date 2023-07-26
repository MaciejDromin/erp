package pl.mlisowski.finances.currency.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mlisowski.finances.currency.application.CurrencyService;
import pl.mlisowski.finances.currency.domain.dto.CurrencyEntryDto;

@RestController
@RequestMapping("/finances/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public Page<CurrencyEntryDto> getPage(@PageableDefault(size = 20) Pageable pageable) {
        return currencyService.getPage(pageable);
    }

}
