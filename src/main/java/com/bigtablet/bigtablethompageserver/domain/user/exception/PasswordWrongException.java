package com.bigtablet.bigtablethompageserver.domain.user.exception;

import com.bigtablet.bigtablethompageserver.domain.user.exception.error.UserError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class PasswordWrongException extends BusinessException {

    public static final PasswordWrongException EXCEPTION = new PasswordWrongException();

    private PasswordWrongException() {
        super(UserError.PASSWORD_WRONG);
    }

}
