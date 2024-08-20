package com.soitio.finances.moneyoperation.web;

import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import java.time.Month;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/widgets")
@RequiredArgsConstructor
public class FilterDataController {

    private final MoneyOperationService moneyOperationService;

    @GetMapping("/years")
    public List<Integer> getDistinctYears() {
        return moneyOperationService.findDistinctEffectiveYears();
    }

    @GetMapping("/months")
    public List<Month> getDistinctMonths(@RequestParam("year") Integer year) {
        return moneyOperationService.findDistinctEffectiveMonths(year);
    }

}
