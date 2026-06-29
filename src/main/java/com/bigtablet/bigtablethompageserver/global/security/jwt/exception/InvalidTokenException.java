package com.bigtablet.bigtablethompageserver.global.security.jwt.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.security.jwt.exception.error.JwtTokenError;

public class InvalidTokenException extends BusinessException {

    public static final InvalidTokenException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(JwtTokenError.INVALID_TOKEN);
    }
}
