package com.bigtablet.bigtablethompageserver.domain.job.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.query.JobQueryService;
import com.bigtablet.bigtablethompageserver.domain.job.application.response.JobResponse;
import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.GetJobListRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobUseCase {

    private final JobService jobService;
    private final JobQueryService jobQueryService;

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

    public List<JobResponse> getJobList(GetJobListRequest request) {
        return jobQueryService.getJobList(
                request.getPage(),
                request.getSize(),
                request.getTitle(),
                request.getDepartment(),
                request.getEducation(),
                request.getRecruitType()
        )
        .stream()
        .map(JobResponse::of)
        .toList();
    }

    public List<JobResponse> getDeactivateJobList(GetJobListRequest request) {
        return jobQueryService.getDeactivateJobList(
                request.getPage(),
                request.getSize(),
                request.getTitle(),
                request.getDepartment(),
                request.getEducation(),
                request.getRecruitType()
        )
        .stream()
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
