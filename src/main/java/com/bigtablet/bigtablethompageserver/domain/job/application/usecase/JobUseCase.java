package com.bigtablet.bigtablethompageserver.domain.job.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.response.JobResponse;
import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobUseCase {

    private final JobService jobService;

    public void registerJob(RegisterJobRequest request) {
        jobService.saveJob(
                request.title(),
                request.department(),
                request.location(),
                request.recruitType(),
                request.experiment(),
                request.education(),
                request.companyIntroduction(),
                request.positionIntroduction(),
                request.mainResponsibility(),
                request.qualification(),
                request.preferredQualification(),
                request.startDate(),
                request.endDate()
        );
    }

    public JobResponse getJob(Long idx) {
        Job job = jobService.findById(idx);
        return JobResponse.of(job);
    }

    public List<JobResponse> getAllJob() {
        List<Job> jobs = jobService.findAllActive();
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public List<JobResponse> searchJobByTitle(String title) {
        List<Job> jobs = jobService.findByTitle(title);
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public List<JobResponse> searchJobByDepartment(Department department) {
        List<Job> jobs = jobService.findByDepartment(department);
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public List<JobResponse> searchJobByEducation(Education education) {
        List<Job> jobs = jobService.findByEducation(education);
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public List<JobResponse> searchJobByRecruitType(RecruitType recruitType) {
        List<Job> jobs = jobService.findByRecruitType(recruitType);
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public List<JobResponse> getAllJobIsFalse() {
        List<Job> jobs = jobService.findAllInactive();
        checkJobsIsEmpty(jobs);
        return jobs.stream()
                .map(JobResponse::of)
                .toList();
    }

    public void editJob(EditJobRequest request) {
        jobService.updateJob(
                request.idx(),
                request.title(),
                request.department(),
                request.location(),
                request.recruitType(),
                request.experiment(),
                request.education(),
                request.companyIntroduction(),
                request.positionIntroduction(),
                request.mainResponsibility(),
                request.qualification(),
                request.preferredQualification(),
                request.startDate(),
                request.endDate()
        );
    }

    public void deleteJob(Long idx) {
        Job job = jobService.findById(idx);
        jobService.deleteById(job.idx());
    }

    private void checkJobsIsEmpty(List<Job> jobs) {
        if (jobs.isEmpty()) {
            throw JobNotFoundException.EXCEPTION;
        }
    }

}
