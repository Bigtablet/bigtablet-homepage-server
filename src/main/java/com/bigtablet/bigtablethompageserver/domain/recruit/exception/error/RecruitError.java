package com.bigtablet.bigtablethompageserver.domain.recruit.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecruitError implements ErrorProperty {

    RECRUIT_NOT_FOUND(HttpStatus.NOT_FOUND, "지원자를 찾을 수 없습니다."),
    RECRUIT_STATUS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서류 상태인 사용자는 최종 합격 상태로 수정이 불가능합니다.");

    private final HttpStatus status;
    private final String message;

}
