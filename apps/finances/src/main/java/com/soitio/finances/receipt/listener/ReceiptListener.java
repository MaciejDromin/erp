package com.soitio.finances.receipt.listener;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.soitio.finances.receipt.application.ReceiptService;
import com.soitio.finances.receipt.domain.dto.ReceiptDto;

@Component
@RequiredArgsConstructor
public class ReceiptListener {

    private final ReceiptService receiptService;

    @RabbitListener(queues = "receipt_queue")
    public void processMessage(List<ReceiptDto> content) {
        receiptService.processReceipts(content);
    }

}
