package com.shirobokov.qr_redirect_microservice.entity;

import com.shirobokov.qr_redirect_microservice.entity.enums.QrType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

@Entity
@Data
@Table(name="qr_routes")
public class QrRoute {

    @Id
    @Column(name="qr_code_id")
    private UUID qrCodeId;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name="type")
    private QrType type;

    @Column(name="redirect_url")
    private String redirectUrl;

}
