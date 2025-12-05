package com.bigtablet.bigtablethompageserver.domain.talent.exception;

import com.bigtablet.bigtablethompageserver.domain.talent.exception.error.TalentError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class TalentNotFoundException extends BusinessException {

    public static final TalentNotFoundException EXCEPTION = new TalentNotFoundException();

    private TalentNotFoundException(){
        super(TalentError.TALENT_NOT_FOUND);
    }

}
