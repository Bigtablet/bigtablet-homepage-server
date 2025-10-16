package com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileError implements ErrorProperty {

    FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "파일이 비워져 있습니다."),
    FILE_WRONG_TYPE(HttpStatus.BAD_REQUEST, "잘못된 파일 종류입니다."),
    FILE_ERROR(HttpStatus.BAD_REQUEST, "손상된 파일입니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}
