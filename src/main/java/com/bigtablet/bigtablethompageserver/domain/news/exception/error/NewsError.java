package com.bigtablet.bigtablethompageserver.domain.news.exception.error;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NewsError implements ErrorProperty {

    NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, "뉴스를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

}
