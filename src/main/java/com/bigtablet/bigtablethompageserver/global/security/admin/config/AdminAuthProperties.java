package com.bigtablet.bigtablethompageserver.global.security.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.admin")
public record AdminAuthProperties(
        // 이메일 OTP TTL (초 단위)
        int otpTtlSeconds,
        // OTP 검증 성공 후 인증 완료 윈도 TTL (초 단위)
        int certTtlSeconds,
        // WebAuthn 챌린지 캐싱 TTL (초 단위)
        int challengeTtlSeconds
) {}
