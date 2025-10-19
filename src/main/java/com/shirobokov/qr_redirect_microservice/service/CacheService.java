package com.shirobokov.qr_redirect_microservice.service;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, QrRoute> redisTemplate;

    private final static int DURATION_TIME_MINUTE = 3;

    public QrRoute get(UUID qrCodeId) {
        return redisTemplate.opsForValue().get(qrCodeId.toString());
    }

    public void save(QrRoute qrRoute) {
        redisTemplate.opsForValue().set(qrRoute.getQrCodeId().toString(), qrRoute, Duration.ofMinutes(DURATION_TIME_MINUTE));
    }

    public boolean delete(UUID qrRouteId) {
        return Boolean.TRUE.equals(redisTemplate.delete(qrRouteId.toString()));
    }
}
