package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class WebAuthnRegistrationFailedException extends BusinessException {

    public static final WebAuthnRegistrationFailedException EXCEPTION = new WebAuthnRegistrationFailedException();

    private WebAuthnRegistrationFailedException() {
        super(AdminError.WEBAUTHN_REGISTRATION_FAILED);
    }

}
