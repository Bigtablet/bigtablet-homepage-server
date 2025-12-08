package com.bigtablet.bigtablethompageserver.domain.talent.exception;

import com.bigtablet.bigtablethompageserver.domain.talent.exception.error.TalentError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class TalentIsEmptyException extends BusinessException {

    public static final TalentIsEmptyException EXCEPTION = new TalentIsEmptyException();

    private TalentIsEmptyException(){
        super(TalentError.TALENT_IS_EMPTY);
    }

}
