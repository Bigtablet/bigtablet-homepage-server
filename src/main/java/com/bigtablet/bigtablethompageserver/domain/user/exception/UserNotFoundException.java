package com.bigtablet.bigtablethompageserver.domain.user.exception;

import com.bigtablet.bigtablethompageserver.domain.user.exception.error.UserError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException(){
        super(UserError.USER_NOT_FOUND);
    }

}
