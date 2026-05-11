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
     * @param adminId String 어드민 ID
     * @param credentialId String Credential ID (Base64URL)
     * @param publicKeyCose String 공개키 (Base64)
     * @param signatureCount long 서명 카운터
     * @param keyName String 키 이름
     * @return void
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
     * @param credentialId String Credential ID (Base64URL)
     * @param signatureCount long 새 서명 카운터
     * @return void
     */
    @Transactional
    public void editSignatureCount(String credentialId, long signatureCount) {
        WebAuthnCredentialEntity entity = webAuthnJpaRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> WebAuthnCredentialNotFoundException.EXCEPTION);
        entity.editSignatureCount(signatureCount);
    }

}
