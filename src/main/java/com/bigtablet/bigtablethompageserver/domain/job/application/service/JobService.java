package com.bigtablet.bigtablethompageserver.domain.job.application.service;

import com.bigtablet.bigtablethompageserver.domain.job.client.dto.Job;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import jakarta.transaction.Transactional;

import java.util.List;

public interface JobService {

    void saveJob(JobEntity jobEntity);

    Job getJob(Long idx);

    List<Job> getAllJob();

    List<Job> searchJobByTitle(String title);

    List<Job> searchJobByDepartment(Department department);

    List<Job> searchJobByEducation(Education education);

    List<Job> searchJobByRecruitType(RecruitType recruitType);

    void deleteJob(Long idx);

    @Transactional
    void editJob(EditJobRequest request);

    void checkJobsIsEmpty(List<Job> jobs);

}
