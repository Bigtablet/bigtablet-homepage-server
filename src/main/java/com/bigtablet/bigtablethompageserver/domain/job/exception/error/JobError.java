package com.bigtablet.bigtablethompageserver.domain.job.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JobError implements ErrorProperty {

    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "공고를 찾을 수 없습니다."),
    JOB_IS_EXPIRED(HttpStatus.BAD_REQUEST, "마감된 공고입니다.");

    private final HttpStatus status;
    private final String message;

}
