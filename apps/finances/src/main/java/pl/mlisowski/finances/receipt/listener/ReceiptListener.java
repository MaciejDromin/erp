package pl.mlisowski.finances.receipt.listener;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.mlisowski.finances.receipt.application.ReceiptService;
import pl.mlisowski.finances.receipt.domain.dto.ReceiptDto;

@Component
@RequiredArgsConstructor
public class ReceiptListener {

    private final ReceiptService receiptService;

    @RabbitListener(queues = "receipt_queue")
    public void processMessage(List<ReceiptDto> content) {
        receiptService.processReceipts(content);
    }

}
