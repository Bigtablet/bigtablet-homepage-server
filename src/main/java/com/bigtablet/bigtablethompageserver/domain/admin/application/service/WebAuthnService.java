package com.bigtablet.bigtablethompageserver.domain.admin.application.service;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.WebAuthnCredentialEntity;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.WebAuthnJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.WebAuthnCredentialNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebAuthnService {

    private final WebAuthnJpaRepository webAuthnJpaRepository;

    /**
     * WebAuthn 크레덴셜 저장
     * @param adminId 어드민 ID
     * @param credentialId Credential ID (Base64URL)
     * @param publicKeyCose 공개키 (Base64)
     * @param signatureCount 서명 카운터
     * @param keyName 키 이름
     */
    @Transactional
    public void save(
            String adminId,
            String credentialId,
            String publicKeyCose,
            long signatureCount,
            String keyName
    ) {
        log.info("[WebAuthnService] save - adminId={}, credentialId={}", adminId, credentialId);
        webAuthnJpaRepository.save(WebAuthnCredentialEntity.builder()
                .adminId(adminId)
                .credentialId(credentialId)
                .publicKeyCose(publicKeyCose)
                .signatureCount(signatureCount)
                .keyName(keyName)
                .build());
    }

    /**
     * 서명 카운터 수정 (replay 방지)
     * @param credentialId Credential ID (Base64URL)
     * @param signatureCount 새 서명 카운터
     */
    @Transactional
    public void editSignatureCount(String credentialId, long signatureCount) {
        WebAuthnCredentialEntity entity = webAuthnJpaRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> WebAuthnCredentialNotFoundException.EXCEPTION);
        entity.editSignatureCount(signatureCount);
    }

}
