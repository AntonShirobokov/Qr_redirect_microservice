package com.shirobokov.qr_redirect_microservice.service;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.exception.QrRouteNotFound;
import com.shirobokov.qr_redirect_microservice.repository.QrRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrRouteService {

    private final QrRouteRepository qrRouteRepository;

    public void save(QrRoute qrRoute) {
        qrRouteRepository.save(qrRoute);
        log.info("Добавление в БД сущности: {}", qrRoute);
    }


    public QrRoute getQrRouteById(UUID qrCodeId) {
        Optional<QrRoute> qrRoute = qrRouteRepository.findQrRoutesByQrCodeId(qrCodeId);
        if (qrRoute.isEmpty()) {
            throw new QrRouteNotFound("QrCode с таким id не найден");
        }
        return qrRoute.get();
    }

    public boolean deleteQrRoute(UUID qrCodeId) {
        return qrRouteRepository.deleteByQrCodeId(qrCodeId) > 0;
    }
}
