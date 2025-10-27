package com.bigtablet.bigtablethompageserver.domain.job.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.client.dto.Job;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobUseCase {

    public final JobService jobService;

    public void registerJob(RegisterJobRequest request) {
        jobService.saveJob(JobEntity.builder()
                .title(request.title())
                .department(request.department())
                .location(request.location())
                .recruitType(request.recruitType())
                .experiment(request.experiment())
                .education(request.education())
                .companyIntroduction(request.companyIntroduction())
                .mainResponsibility(request.mainResponsibility())
                .qualification(request.qualification())
                .preferredQualification(request.preferredQualification())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .isActive(true)
                .build());
    }

    public Job getJob(Long idx) {
        return jobService.getJob(idx);
    }

    public List<Job> getAllJob() {
        List<Job> jobs = jobService.getAllJob();
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public List<Job> searchJobByTitle(String title) {
        List<Job> jobs = jobService.searchJobByTitle(title);
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public List<Job> searchJobByDepartment(Department department) {
        List<Job> jobs = jobService.searchJobByDepartment(department);
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public List<Job> searchJobByEducation(Education education) {
        List<Job> jobs = jobService.searchJobByEducation(education);
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public List<Job> searchJobByRecruitType(RecruitType recruitType) {
        List<Job> jobs = jobService.searchJobByRecruitType(recruitType);
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public List<Job> getAllJobIsFalse() {
        List<Job> jobs = jobService.getAllJobIsFalse();
        jobService.checkJobsIsEmpty(jobs);
        return jobs;
    }

    public void editJob(EditJobRequest request) {
        jobService.editJob(request);
    }

    public void deleteJob(Long idx) {
        Job job = getJob(idx);
        jobService.deleteJob(job.idx());
    }

}
