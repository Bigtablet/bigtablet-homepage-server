package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class EmailCodeMismatchException extends BusinessException {

    public static final EmailCodeMismatchException EXCEPTION = new EmailCodeMismatchException();

    private EmailCodeMismatchException() {
        super(AdminError.EMAIL_CODE_MISMATCH);
    }

}
