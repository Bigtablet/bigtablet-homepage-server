package com.bigtablet.bigtablethompageserver.domain.admin.exception;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.error.AdminError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class AdminNotFoundException extends BusinessException {

    public static final AdminNotFoundException EXCEPTION = new AdminNotFoundException();

    private AdminNotFoundException() {
        super(AdminError.ADMIN_NOT_FOUND);
    }

}
