package com.bigtablet.bigtablethompageserver.domain.job.application.query;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query.JobQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobIsExpiredException;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobQueryService {

    private final JobJpaRepository jobJpaRepository;
    private final JobQueryRepository jobQueryRepository;

    public Job find(Long idx) {
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        return Job.of(entity);
    }

    public void checkIsExpired(Job job) {
        if (!job.isActive()) {
            throw JobIsExpiredException.EXCEPTION;
        }
    }

    public List<Job> findJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findJobList(
                page,
                size,
                title,
                department,
                education,
                recruitType
        );
    }

    public List<Job> findDeactivateJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findDeactivateJobList(
                page,
                size,
                title,
                department,
                education,
                recruitType
        );
    }

}
