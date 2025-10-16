package com.shirobokov.qr_redirect_microservice.rabbitmq.listener;

import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.service.QrRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class QrRouteListener {

    static final String QUEUE_NAME_1 = "redirect_queue";
    static final String QUEUE_NAME_2 = "delete_queue";

    private final QrRouteService qrRouteService;

    @RabbitListener(queues = {QUEUE_NAME_1})
    public void getQrRouteForSave(QrRoute qrRoute) {
        log.info("Получено сообщение: {}", qrRoute.toString());
        qrRouteService.save(qrRoute);
    }

    @RabbitListener(queues = {QUEUE_NAME_2})
    public void getQrCodeIdForDelete(HashMap<String, UUID> qrCodeData) {
        UUID qrCodeId = qrCodeData.get("qrCodeId");
        log.info("Получен id qr кода для удаления: {}", qrCodeId);

        boolean result = qrRouteService.deleteQrRoute(qrCodeId);

        log.info(result ? "QR-код с id {} был удалён" : "Ничего не удалено (id: {})", qrCodeId);
    }
}
