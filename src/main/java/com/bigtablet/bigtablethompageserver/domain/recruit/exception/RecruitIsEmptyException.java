package com.bigtablet.bigtablethompageserver.domain.recruit.exception;

import com.bigtablet.bigtablethompageserver.domain.recruit.exception.error.RecruitError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class RecruitIsEmptyException extends BusinessException {

    public static final RecruitIsEmptyException EXCEPTION = new RecruitIsEmptyException();

    private RecruitIsEmptyException(){
        super(RecruitError.RECRUIT_IS_EMPTY);
    }

}
