package com.bigtablet.bigtablethompageserver.global.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        String message = exception
                .getBindingResult()
                .getAllErrors()
                .getFirst()
                .getDefaultMessage();
        return ErrorResponse.of(message);
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

    private record ErrorResponse(int status, String message) {
        public static ErrorResponse of(String message) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        }
    }

}
