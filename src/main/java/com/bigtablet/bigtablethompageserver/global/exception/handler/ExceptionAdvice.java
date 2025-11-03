package com.bigtablet.bigtablethompageserver.global.exception.handler;

import com.bigtablet.bigtablethompageserver.global.common.dto.response.ErrorResponse;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponse> handleException(BusinessException ex){
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getError().getStatus().value())
                .message(ex.getError().getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(response, ex.getError().getStatus());
    }

}
