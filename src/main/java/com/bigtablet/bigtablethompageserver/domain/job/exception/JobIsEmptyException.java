package com.bigtablet.bigtablethompageserver.domain.job.exception;

import com.bigtablet.bigtablethompageserver.domain.job.exception.error.JobError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class JobIsEmptyException extends BusinessException {

    public static final JobIsEmptyException EXCEPTION = new JobIsEmptyException();

    private JobIsEmptyException(){
        super(JobError.JOB_IS_EMPTY);
    }

}