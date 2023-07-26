package pl.mlisowski.finances.moneyoperation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mlisowski.finances.moneyoperation.application.MoneyOperationService;
import pl.mlisowski.finances.moneyoperation.domain.dto.MoneyOperationCreationDto;
import pl.mlisowski.finances.moneyoperation.domain.dto.MoneyOperationDto;

@RestController
@RequestMapping("/finances/money-operation")
@RequiredArgsConstructor
public class MoneyOperationController {

    private final MoneyOperationService operationService;

    @GetMapping
    public Page<MoneyOperationDto> getMoneyOpeations(@PageableDefault(size = 20) Pageable pageable) {
        return operationService.getPage(pageable);
    }

    @PostMapping
    public void registerMoneyOperation(@RequestBody MoneyOperationCreationDto creation) {
        operationService.create(creation);
    }

}
