package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class WebAuthnCredentialNotFoundException extends BusinessException {

    public static final WebAuthnCredentialNotFoundException EXCEPTION = new WebAuthnCredentialNotFoundException();

    private WebAuthnCredentialNotFoundException() {
        super(AdminError.WEBAUTHN_CREDENTIAL_NOT_FOUND);
    }

}
