package com.bigtablet.bigtablethompageserver.domain.user.exception;

import com.bigtablet.bigtablethompageserver.domain.user.exception.error.UserError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class UserAlreadyExistException extends BusinessException {

    public static final UserAlreadyExistException EXCEPTION = new UserAlreadyExistException();

    public UserAlreadyExistException() {
        super(UserError.USER_ALREADY_EXIST);
    }

}
