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

    private final CacheService cacheService;


    public void save(QrRoute qrRoute) {
        qrRouteRepository.save(qrRoute);
        log.info("Добавление в БД сущности: {}", qrRoute);
    }


    public QrRoute getQrRouteById(UUID qrCodeId) {

        QrRoute qrRoute = cacheService.get(qrCodeId);
        if (qrRoute!=null) {
            log.info("QrRoute получен из Redis: {}", qrRoute);
            return qrRoute;
        }

        Optional<QrRoute> optionalQrRoute = qrRouteRepository.findQrRoutesByQrCodeId(qrCodeId);
        qrRoute = optionalQrRoute.orElseThrow(
                () -> new QrRouteNotFound("QrCode с таким id не найден")
        );

        cacheService.save(qrRoute);
        log.info("QrRoute получен из БД: {}", qrRoute);

        return optionalQrRoute.get();
    }

    public boolean deleteQrRoute(UUID qrCodeId) {
        boolean isDeleted = cacheService.delete(qrCodeId);

        log.info(isDeleted ? "QrRoute с id: {}, был удален из кеша" : "QrRoute c id: {}, не было в кеше", qrCodeId);

        return qrRouteRepository.deleteByQrCodeId(qrCodeId) > 0;
    }
}
