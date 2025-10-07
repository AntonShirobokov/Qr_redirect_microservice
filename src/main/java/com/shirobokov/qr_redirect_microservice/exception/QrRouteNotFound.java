package com.shirobokov.qr_redirect_microservice.exception;

public class QrRouteNotFound extends RuntimeException {
    public QrRouteNotFound(String message) {
        super(message);
    }
}
