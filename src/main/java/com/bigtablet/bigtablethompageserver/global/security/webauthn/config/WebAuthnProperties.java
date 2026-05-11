package com.bigtablet.bigtablethompageserver.global.security.webauthn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "application.webauthn")
public record WebAuthnProperties(
        String rpId,
        String rpName,
        List<String> allowedOrigins
) {}
