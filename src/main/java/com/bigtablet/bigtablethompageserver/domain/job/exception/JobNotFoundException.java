package com.bigtablet.bigtablethompageserver.domain.job.exception;

import com.bigtablet.bigtablethompageserver.domain.job.exception.error.JobError;
import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;

public class JobNotFoundException extends BusinessException {

    public static final JobNotFoundException EXCEPTION = new JobNotFoundException();

    private JobNotFoundException(){
        super(JobError.JOB_NOT_FOUND);
    }

}
