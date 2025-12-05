package com.bigtablet.bigtablethompageserver.domain.talent.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TalentError implements ErrorProperty {

    TALENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 인재를 찾을 수 없습니다."),
    TALENT_IS_EMPTY(HttpStatus.NO_CONTENT, "등록된 인재가 없습니다.");

    private final HttpStatus status;
    private final String message;

}
