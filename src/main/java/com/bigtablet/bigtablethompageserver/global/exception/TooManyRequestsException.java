package com.bigtablet.bigtablethompageserver.global.exception;

import com.bigtablet.bigtablethompageserver.global.exception.error.ErrorCode;

public class TooManyRequestsException extends BusinessException {

    public static final TooManyRequestsException EXCEPTION = new TooManyRequestsException();

    private TooManyRequestsException() {
        super(ErrorCode.TOO_MANY_REQUESTS);
    }

}
