package com.shirobokov.qr_redirect_microservice.restcontroller;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import com.shirobokov.qr_redirect_microservice.service.QrRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/redirect")
@RequiredArgsConstructor
public class RedirectController {

    private final QrRouteService qrRouteService;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> redirect(@PathVariable("uuid")UUID qrCodeId) {
        QrRoute qrRoute = qrRouteService.getQrRouteById(qrCodeId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(qrRoute.getRedirectUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
