package com.shirobokov.qr_redirect_microservice.restcontroller;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.event.ScanEvent;
import com.shirobokov.qr_redirect_microservice.rabbitmq.producer.AnalyticsProducer;
import com.shirobokov.qr_redirect_microservice.service.QrRouteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/redirect")
@RequiredArgsConstructor
public class RedirectController {

    private final QrRouteService qrRouteService;

    private final AnalyticsProducer analyticsProducer;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> redirect(@PathVariable("uuid")UUID qrCodeId, HttpServletRequest httpServletRequest) throws InterruptedException {
        QrRoute qrRoute = qrRouteService.getQrRouteById(qrCodeId);

        ScanEvent scanEvent = new ScanEvent(
                qrCodeId,
                httpServletRequest.getRemoteAddr(),
                httpServletRequest.getHeader("User-Agent"),
                LocalDateTime.now());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(qrRoute.getRedirectUrl()));
        httpHeaders.setCacheControl(CacheControl.noStore());
        analyticsProducer.send(scanEvent);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
