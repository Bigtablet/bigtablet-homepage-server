package com.bigtablet.bigtablethompageserver.global.security.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.admin")
public record AdminAuthProperties(
        // 이메일 OTP 유효 기간 (예: 3m)
        Duration otpTtl,
        // OTP 검증 성공 후 인증 완료 윈도 (예: 30m)
        Duration certTtl,
        // WebAuthn 챌린지 캐싱 유효 기간 (예: 5m)
        Duration challengeTtl
) {}
