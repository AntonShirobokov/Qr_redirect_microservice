package com.shirobokov.qr_redirect_microservice.repository;


import com.shirobokov.qr_redirect_microservice.entity.QrRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrRouteRepository extends JpaRepository<QrRoute, UUID> {

    Optional<QrRoute> findQrRoutesByQrCodeId(UUID uuid);

    @Transactional
    Integer deleteByQrCodeId(UUID qrCodeId);
}
