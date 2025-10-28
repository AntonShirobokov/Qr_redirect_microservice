package com.shirobokov.qr_redirect_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ScanEvent {
    private UUID qrCodeId;
    private String ipAddress;
    private String client;
    private LocalDateTime scanAt;
}
