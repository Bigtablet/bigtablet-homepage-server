package com.bigtablet.bigtablethompageserver.domain.recruit.exception;

import com.bigtablet.bigtablethompageserver.domain.recruit.exception.error.RecruitError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class RecruitNotFoundException extends BusinessException {

    public static final RecruitNotFoundException EXCEPTION = new RecruitNotFoundException();

    private RecruitNotFoundException(){
        super(RecruitError.RECRUIT_NOT_FOUND);
    }

}
