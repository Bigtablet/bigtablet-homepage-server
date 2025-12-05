package com.bigtablet.bigtablethompageserver.domain.talent.exception;

import com.bigtablet.bigtablethompageserver.domain.talent.exception.error.TalentError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class TalentAlreadyExistException extends BusinessException {

    public static final TalentAlreadyExistException EXCEPTION = new TalentAlreadyExistException();

    private TalentAlreadyExistException() {
        super(TalentError.TALENT_ALREADY_EXIST);
    }

}