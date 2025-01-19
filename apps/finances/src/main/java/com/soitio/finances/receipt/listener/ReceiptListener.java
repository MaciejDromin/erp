package com.soitio.finances.receipt.listener;

import com.soitio.finances.receipt.application.ReceiptService;
import com.soitio.finances.receipt.domain.dto.OrgWrapper;
import com.soitio.finances.receipt.domain.dto.ReceiptDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptListener {

    private final ReceiptService receiptService;

    @RabbitListener(queues = "receipt_queue")
    public void processMessage(OrgWrapper<List<ReceiptDto>> content) {
        receiptService.processReceipts(content);
    }

}
