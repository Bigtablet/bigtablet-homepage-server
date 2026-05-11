package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class CredentialAlreadyRegisteredException extends BusinessException {

    public static final CredentialAlreadyRegisteredException EXCEPTION = new CredentialAlreadyRegisteredException();

    private CredentialAlreadyRegisteredException() {
        super(AdminError.CREDENTIAL_ALREADY_REGISTERED);
    }

}
