package com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.adapter;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.WebAuthnCredentialEntity;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.repository.jpa.WebAuthnJpaRepository;
import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.exception.Base64UrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebAuthnCredentialRepositoryAdapter implements CredentialRepository {

    private final WebAuthnJpaRepository webAuthnJpaRepository;

    /**
     * username(=adminId)에 등록된 모든 크레덴셜 ID 목록 조회
     * @param username 어드민 ID
     * @return 크레덴셜 디스크립터 목록
     */
    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        List<WebAuthnCredentialEntity> entities = webAuthnJpaRepository.findAllByAdminId(username);
        return entities.stream()
                .map(entity -> PublicKeyCredentialDescriptor.builder()
                        .id(decodeBase64Url(entity.getCredentialId()))
                        .build())
                .collect(Collectors.toSet());
    }

    /**
     * username(=adminId)을 ByteArray user handle로 변환
     * @param username 어드민 ID
     * @return 유저 핸들
     */
    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        if (webAuthnJpaRepository.existsByAdminId(username)) {
            return Optional.of(new ByteArray(username.getBytes(StandardCharsets.UTF_8)));
        }
        return Optional.empty();
    }

    /**
     * ByteArray user handle을 username(=adminId)로 변환
     * @param userHandle 유저 핸들
     * @return 어드민 ID
     */
    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        String adminId = new String(userHandle.getBytes(), StandardCharsets.UTF_8);
        if (webAuthnJpaRepository.existsByAdminId(adminId)) {
            return Optional.of(adminId);
        }
        return Optional.empty();
    }

    /**
     * credentialId + userHandle로 RegisteredCredential 조회
     * @param credentialId 크레덴셜 ID
     * @param userHandle 유저 핸들
     * @return 등록된 크레덴셜
     */
    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        String credentialIdBase64Url = credentialId.getBase64Url();
        return webAuthnJpaRepository.findByCredentialId(credentialIdBase64Url)
                .map(this::buildRegisteredCredential);
    }

    /**
     * credentialId로 모든 RegisteredCredential 조회
     * @param credentialId 크레덴셜 ID
     * @return 등록된 크레덴셜 집합
     */
    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        String credentialIdBase64Url = credentialId.getBase64Url();
        return webAuthnJpaRepository.findByCredentialId(credentialIdBase64Url)
                .map(this::buildRegisteredCredential)
                .map(Set::of)
                .orElse(Set.of());
    }

    // RegisteredCredential 빌드
    private RegisteredCredential buildRegisteredCredential(WebAuthnCredentialEntity entity) {
        return RegisteredCredential.builder()
                .credentialId(decodeBase64Url(entity.getCredentialId()))
                .userHandle(new ByteArray(entity.getAdminId().getBytes(StandardCharsets.UTF_8)))
                .publicKeyCose(new ByteArray(Base64.getDecoder().decode(entity.getPublicKeyCose())))
                .signatureCount(entity.getSignatureCount())
                .build();
    }

    // Base64URL 문자열을 ByteArray로 디코딩
    private ByteArray decodeBase64Url(String base64Url) {
        try {
            return ByteArray.fromBase64Url(base64Url);
        } catch (Base64UrlException e) {
            throw new IllegalArgumentException("Invalid Base64URL string: " + base64Url, e);
        }
    }

}
