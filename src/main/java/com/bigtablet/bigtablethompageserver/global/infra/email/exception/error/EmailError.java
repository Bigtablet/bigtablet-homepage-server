package com.bigtablet.bigtablethompageserver.global.infra.email.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmailError implements ErrorProperty {

    EMAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 에러"),
    EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST, "유효하지 않은 코드");

    private final HttpStatus status;
    private final String message;

}
