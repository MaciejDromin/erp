package com.soitio.finances.currency.web;

import com.soitio.finances.currency.application.CurrencyService;
import com.soitio.finances.currency.domain.dto.CurrencyEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public Page<CurrencyEntryDto> getPage(@PageableDefault(size = 20) Pageable pageable) {
        return currencyService.getPage(pageable);
    }

}
