package com.bigtablet.bigtablethompageserver.global.security.webauthn.config;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebAuthnProperties.class)
public class WebAuthnConfig {

    private final WebAuthnProperties properties;
    private final CredentialRepository credentialRepository;

    /**
     * Yubico RelyingParty 빈 등록
     * @return WebAuthn RP 인스턴스
     */
    @Bean
    public RelyingParty relyingParty() {
        log.info("[WebAuthnConfig] 설정 초기화 - rpId={}, rpName={}, allowedOrigins={}",
                properties.rpId(), properties.rpName(), properties.allowedOrigins());
        return RelyingParty.builder()
                .identity(RelyingPartyIdentity.builder()
                        .id(properties.rpId())
                        .name(properties.rpName())
                        .build())
                .credentialRepository(credentialRepository)
                .origins(new LinkedHashSet<>(properties.allowedOrigins()))
                .build();
    }

}
