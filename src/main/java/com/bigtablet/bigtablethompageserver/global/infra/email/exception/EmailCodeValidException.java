package com.bigtablet.bigtablethompageserver.global.infra.email.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.error.EmailError;

public class EmailCodeValidException extends BusinessException {

    public static final EmailCodeValidException EXCEPTION =  new EmailCodeValidException();

    private EmailCodeValidException() {
        super(EmailError.EMAIL_NOT_VALID);
    }

}
