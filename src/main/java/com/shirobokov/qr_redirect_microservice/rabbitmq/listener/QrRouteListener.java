package com.shirobokov.qr_redirect_microservice.rabbitmq.listener;

import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.service.QrRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QrRouteListener {

    static final String EXCHANGE_NAME = "redirect_exchange";
    static final String QUEUE_NAME = "redirect_queue";
    public static final String ROUTING_KEY = "qr.redirect";

    private final QrRouteService qrRouteService;


    @RabbitListener(queues = {QUEUE_NAME})
    public void handle(QrRoute qrRoute) {
        log.info("Получено сообщение: {}", qrRoute.toString());
        qrRouteService.save(qrRoute);
    }

}
