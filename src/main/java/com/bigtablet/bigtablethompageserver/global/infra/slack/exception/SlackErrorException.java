package com.bigtablet.bigtablethompageserver.global.infra.slack.exception;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.error.EmailError;

public class SlackErrorException extends BusinessException {

    public static final SlackErrorException EXCEPTION = new SlackErrorException();

    private SlackErrorException(){
        super(EmailError.EMAIL_ERROR);
    }

}
