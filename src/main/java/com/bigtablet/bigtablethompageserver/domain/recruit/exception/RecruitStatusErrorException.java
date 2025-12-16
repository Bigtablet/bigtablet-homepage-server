package com.bigtablet.bigtablethompageserver.domain.recruit.exception;

import com.bigtablet.bigtablethompageserver.domain.recruit.exception.error.RecruitError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class RecruitStatusErrorException extends BusinessException {

    public static final RecruitStatusErrorException EXCEPTION = new RecruitStatusErrorException();

    private RecruitStatusErrorException(){
        super(RecruitError.RECRUIT_STATUS_ERROR);
    }

}
