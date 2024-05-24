package pl.mlisowski.finances.common.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String topic, Object o) {
        log.info("Publishing message {} to topic {}", o, topic);
        rabbitTemplate.convertAndSend(topic, o);
    }

}
