package com.shirobokov.qr_redirect_microservice.rabbitmq.producer;


import com.shirobokov.qr_redirect_microservice.event.ScanEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final String EXCHANGE_NAME = "analytics_exchange";

    private final RabbitTemplate rabbitTemplate;

    @Async
    public void send(ScanEvent scanEvent) throws InterruptedException {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", scanEvent);

        log.info("Событие о сканировании было добавлено в очередь analytics_exchange: {}", scanEvent);
    }
}
