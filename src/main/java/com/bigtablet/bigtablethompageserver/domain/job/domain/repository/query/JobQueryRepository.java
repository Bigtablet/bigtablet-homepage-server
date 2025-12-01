package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface JobQueryRepository {

    List<Job> findJobList(
            PageRequest request,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    );

    List<Job> findDeactivateJobList(
            PageRequest request,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    );

}
