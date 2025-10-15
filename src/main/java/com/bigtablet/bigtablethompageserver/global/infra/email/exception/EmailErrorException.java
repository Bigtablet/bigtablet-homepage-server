package com.bigtablet.bigtablethompageserver.global.infra.email.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.error.EmailError;

public class EmailErrorException extends BusinessException {

    public static final EmailErrorException EXCEPTION = new EmailErrorException();

    private EmailErrorException(){
        super(EmailError.EMAIL_ERROR);
    }

}