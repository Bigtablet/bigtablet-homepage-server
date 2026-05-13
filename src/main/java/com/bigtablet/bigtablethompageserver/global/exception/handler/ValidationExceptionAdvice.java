package com.bigtablet.bigtablethompageserver.global.exception.handler;

import com.bigtablet.bigtablethompageserver.global.common.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse catchValidationException(MethodArgumentNotValidException exception) {
        return ErrorResponse.of(firstDefaultMessage(exception));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse catchBindException(BindException exception) {
        return ErrorResponse.of(firstDefaultMessage(exception));
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

    /**
     * BindingResult에서 첫 번째 default message를 추출한다.
     * MethodArgumentNotValidException은 BindException의 자식이라 공통 처리 가능.
     * @param exception BindException 검증 예외
     * @return String 첫 검증 오류의 default message
     */
    private String firstDefaultMessage(BindException exception) {
        return exception
                .getBindingResult()
                .getAllErrors()
                .getFirst()
                .getDefaultMessage();
    }

}
