package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class WebAuthnAuthenticationFailedException extends BusinessException {

    public static final WebAuthnAuthenticationFailedException EXCEPTION = new WebAuthnAuthenticationFailedException();

    private WebAuthnAuthenticationFailedException() {
        super(AdminError.WEBAUTHN_AUTHENTICATION_FAILED);
    }

}
