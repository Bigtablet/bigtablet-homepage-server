package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class InvalidEmailDomainException extends BusinessException {

    public static final InvalidEmailDomainException EXCEPTION = new InvalidEmailDomainException();

    private InvalidEmailDomainException() {
        super(AdminError.INVALID_EMAIL_DOMAIN);
    }

}
