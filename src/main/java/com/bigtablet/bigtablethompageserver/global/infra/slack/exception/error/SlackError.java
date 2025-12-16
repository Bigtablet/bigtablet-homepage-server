package com.bigtablet.bigtablethompageserver.global.infra.slack.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SlackError implements ErrorProperty {

    SLACK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 에러");

    private final HttpStatus status;
    private final String message;

}
