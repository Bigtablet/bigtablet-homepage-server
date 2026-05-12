package com.bigtablet.bigtablethompageserver.global.security.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

// application.yml 누락 시 NPE 방지를 위해 @DefaultValue로 안전 기본값 제공 — yml에 명시된 값과 동일하게 유지
@ConfigurationProperties(prefix = "application.admin")
public record AdminAuthProperties(
        // 이메일 OTP 유효 기간 (예: 3m)
        @DefaultValue("3m") Duration otpTtl,
        // OTP 검증 성공 후 인증 완료 윈도 (예: 30m)
        @DefaultValue("30m") Duration certTtl,
        // WebAuthn 챌린지 캐싱 유효 기간 (예: 5m)
        @DefaultValue("5m") Duration challengeTtl
) {}
