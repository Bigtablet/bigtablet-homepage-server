package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class EmailNotCertifiedException extends BusinessException {

    public static final EmailNotCertifiedException EXCEPTION = new EmailNotCertifiedException();

    private EmailNotCertifiedException() {
        super(AdminError.EMAIL_NOT_CERTIFIED);
    }

}
