package com.bigtablet.bigtablethompageserver.global.exception.handler;

import com.bigtablet.bigtablethompageserver.global.common.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionAdvice {

    /**
     * `@RequestBody @Valid`(MethodArgumentNotValidException) / `@ModelAttribute @Valid`(BindException) 양쪽 검증 실패를
     * BindException 부모 핸들러 하나로 통합 처리한다. MethodArgumentNotValidException은 BindException 자식.
     * @param exception BindException 검증 실패 예외
     * @return ErrorResponse 첫 검증 오류 메시지 응답
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse catchBindException(BindException exception) {
        return ErrorResponse.of(
                exception
                        .getBindingResult()
                        .getAllErrors()
                        .getFirst()
                        .getDefaultMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof InvalidFormatException invalidFormatException &&
                invalidFormatException.getTargetType().isEnum()) {
            return ErrorResponse.of("ENUM 값이 올바르지 않습니다.");
        }
        return ErrorResponse.of("요청 본문이 올바르지 않습니다.");
    }

}
