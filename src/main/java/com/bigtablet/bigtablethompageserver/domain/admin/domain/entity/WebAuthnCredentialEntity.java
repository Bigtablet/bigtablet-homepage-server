package com.bigtablet.bigtablethompageserver.domain.admin.domain.entity;

import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_webauthn")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebAuthnCredentialEntity extends BaseEntity {

    // 자동 생성 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 어드민 ID (AdminEntity.id 참조)
    @Column(nullable = false)
    private String adminId;
    // Credential ID (Base64URL 인코딩)
    @Column(nullable = false, unique = true, length = 512)
    private String credentialId;
    // 공개키 (Base64 인코딩, COSE 포맷)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String publicKeyCose;
    // 서명 카운터 (replay 방지)
    @Column(nullable = false)
    private long signatureCount;
    // 키 이름 (디스플레이용)
    private String keyName;

    /**
     * 서명 카운터를 수정한다.
     * @param signatureCount 새 서명 카운터
     */
    public void editSignatureCount(long signatureCount) {
        this.signatureCount = signatureCount;
    }

}
