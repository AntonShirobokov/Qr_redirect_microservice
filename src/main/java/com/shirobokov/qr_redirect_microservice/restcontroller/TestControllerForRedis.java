package com.shirobokov.qr_redirect_microservice.restcontroller;

import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.entity.enums.QrType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestControllerForRedis {

    private final RedisTemplate<String, QrRoute> redisTemplate;

    @GetMapping("/writeToRedis")
    public ResponseEntity<?> writeToRedis() {

        QrRoute qrRoute = new QrRoute();
        qrRoute.setQrCodeId(UUID.fromString("28ec8c44-3508-4867-9c4c-de947cb57c31"));
        qrRoute.setType(QrType.qrWithStatistics);
        qrRoute.setRedirectUrl("https://translate.yandex.ru/");

        redisTemplate.opsForValue().set(qrRoute.getQrCodeId().toString(), qrRoute);

        return ResponseEntity.ok("Успешная запись в redis");
    }

    @GetMapping("/readFromRedis")
    public ResponseEntity<?> readFromRedis() {
        QrRoute qrRoute = redisTemplate.opsForValue().get("28ec8c44-3508-4867-9c4c-de947cb57c31");
        return ResponseEntity.ok(qrRoute);
    }
    @GetMapping("/deleteFromRedis")
    public ResponseEntity<?> deleteFromRedis() {
        Boolean qrRoute = redisTemplate.delete("28ec8c44-3508-4867-9c4c-de947cb57c31");
        log.info("Удаленный qr route: {}", qrRoute);
        return ResponseEntity.ok(qrRoute);
    }

    @GetMapping("/writeToHashRedis")
    public ResponseEntity<?> writeToHashRedis() {
        QrRoute qrRoute1 = new QrRoute();
        qrRoute1.setQrCodeId(UUID.fromString("11111111-3508-4867-9c4c-de947cb57c31"));
        qrRoute1.setType(QrType.qrWithStatistics);
        qrRoute1.setRedirectUrl("https://translate.yandex.ru/");


        QrRoute qrRoute2 = new QrRoute();
        qrRoute2.setQrCodeId(UUID.fromString("22222222-3508-4867-9c4c-de947cb57c31"));
        qrRoute2.setType(QrType.qrWithStatistics);
        qrRoute2.setRedirectUrl("https://gpt.ru/");


        QrRoute qrRoute3 = new QrRoute();
        qrRoute3.setQrCodeId(UUID.fromString("33333333-3508-4867-9c4c-de947cb57c31"));
        qrRoute3.setType(QrType.qrWithStatistics);
        qrRoute3.setRedirectUrl("https://vk.ru/");

        redisTemplate.opsForHash().put("qrRoutes", qrRoute1.getQrCodeId(), qrRoute1);
        redisTemplate.opsForHash().put("qrRoutes", qrRoute2.getQrCodeId(), qrRoute2);
        redisTemplate.opsForHash().put("qrRoutes", qrRoute3.getQrCodeId(), qrRoute3);

        return ResponseEntity.ok("Успешная запись в redis");
    }
    @GetMapping("/readFromHashRedis")
    public ResponseEntity<?> readFromHashRedis() {
        QrRoute qrRoute = (QrRoute) redisTemplate.opsForHash().get("qrRoutes", "11111111-3508-4867-9c4c-de947cb57c31");
        return ResponseEntity.ok(qrRoute);
    }


    @GetMapping("/writeToTTLRedis")
    public ResponseEntity<?> writeToTTLRedis() {

        QrRoute qrRoute = new QrRoute();
        qrRoute.setQrCodeId(UUID.fromString("55555555-3508-4867-9c4c-de947cb57c31"));
        qrRoute.setType(QrType.qrWithStatistics);
        qrRoute.setRedirectUrl("https://shirobokov.yandex.ru/");

        redisTemplate.opsForValue().set(qrRoute.getQrCodeId().toString(), qrRoute, Duration.ofMinutes(1));

        return ResponseEntity.ok("Успешная запись в redis");
    }

    @GetMapping("/readFromTTLRedis")
    public ResponseEntity<?> readFromTTLRedis() {
        QrRoute qrRoute = redisTemplate.opsForValue().get("55555555-3508-4867-9c4c-de947cb57c31");
        log.info("Полученные данные из redis: {}", qrRoute);
        return ResponseEntity.ok( qrRoute!=null ? qrRoute : "Время кончилось");
    }
}
