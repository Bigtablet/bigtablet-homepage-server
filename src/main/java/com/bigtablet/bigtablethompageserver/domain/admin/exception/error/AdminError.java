package com.bigtablet.bigtablethompageserver.domain.admin.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AdminError implements ErrorProperty {

    // 어드민을 찾을 수 없음
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "Admin not found."),
    // 허용되지 않는 이메일 도메인
    INVALID_EMAIL_DOMAIN(HttpStatus.FORBIDDEN, "Email domain is not allowed."),
    // WebAuthn 등록 실패
    WEBAUTHN_REGISTRATION_FAILED(HttpStatus.BAD_REQUEST, "WebAuthn registration failed."),
    // WebAuthn 인증 실패
    WEBAUTHN_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "WebAuthn authentication failed."),
    // WebAuthn 크레덴셜을 찾을 수 없음
    WEBAUTHN_CREDENTIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "WebAuthn credential not found.");

    // HTTP 상태 코드
    private final HttpStatus status;
    // 에러 메시지
    private final String message;

}
