package com.bigtablet.bigtablethompageserver.domain.job.exception;

import com.bigtablet.bigtablethompageserver.domain.job.exception.error.JobError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class JobIsExpiredException extends BusinessException {

    public static final JobIsExpiredException EXCEPTION = new JobIsExpiredException();

    private JobIsExpiredException(){
        super(JobError.JOB_IS_EXPIRED);
    }

}
