package pl.mlisowski.finances.receipt.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.mlisowski.finances.receipt.domain.dto.ReceiptDto;

@Slf4j
@Component
public class ReceiptListener {

    @RabbitListener(queues = "receipt_queue")
    public void processMessage(List<ReceiptDto> content) {
        log.info("ML received notification {}", content);
    }

}
