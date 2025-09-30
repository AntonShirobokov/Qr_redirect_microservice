package com.shirobokov.qr_redirect_microservice.service;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.repository.QrRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrRouteService {

    private final QrRouteRepository qrRouteRepository;

    public void save(QrRoute qrRoute) {
        qrRouteRepository.save(qrRoute);
        log.info("Добавление в БД сущности: {}", qrRoute);
    }
}
