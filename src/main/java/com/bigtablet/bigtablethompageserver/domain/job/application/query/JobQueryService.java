package com.bigtablet.bigtablethompageserver.domain.job.application.query;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query.JobQueryRepository;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobQueryService {

    private final JobQueryRepository jobQueryRepository;

    public List<Job> getJobList(
            PageRequest request,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findJobList(
                request,
                title,
                department,
                education,
                recruitType
        );
    }

    public List<Job> getDeactivateJobList(
            PageRequest request,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findDeactivateJobList(
                request,
                title,
                department,
                education,
                recruitType
        );
    }

}

