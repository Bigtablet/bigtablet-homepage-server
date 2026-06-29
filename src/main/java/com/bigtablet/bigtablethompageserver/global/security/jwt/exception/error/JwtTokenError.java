package com.bigtablet.bigtablethompageserver.global.security.jwt.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtTokenError implements ErrorProperty {

    JWT_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "잘못된 타입"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String message;

}
