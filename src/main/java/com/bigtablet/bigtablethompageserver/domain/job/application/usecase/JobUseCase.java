package com.bigtablet.bigtablethompageserver.domain.job.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.query.JobQueryService;
import com.bigtablet.bigtablethompageserver.domain.job.application.response.JobResponse;
import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.GetJobListRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobIsEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobUseCase {

    private final JobService jobService;
    private final JobQueryService jobQueryService;

    /**
     * 채용 공고 등록
     * @param request RegisterJobRequest 채용 공고 등록 요청 정보
     * @return void
     */
    public void registerJob(RegisterJobRequest request) {
        log.info("[JobUseCase] registerJob - title={}", request.title());
        jobService.save(
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

    /**
     * 채용 공고 단건 조회
     * @param idx Long 채용 공고 식별자
     * @return JobResponse 채용 공고 응답
     */
    public JobResponse getJob(Long idx) {
        Job job = jobQueryService.find(idx);
        return JobResponse.of(job);
    }

    /**
     * 활성 채용 공고 목록 조회
     * @param request GetJobListRequest 채용 공고 목록 조회 요청 정보
     * @return List<JobResponse> 채용 공고 응답 목록
     */
    public List<JobResponse> getJobList(GetJobListRequest request) {
        List<Job> jobs =
                jobQueryService.findJobList(
                        request.getPage(),
                        request.getSize(),
                        request.getTitle(),
                        request.getDepartment(),
                        request.getEducation(),
                        request.getRecruitType()
                );
        checkJobsIsEmpty(jobs);
        return jobs.stream().map(JobResponse::of).toList();
    }

    /**
     * 비활성 채용 공고 목록 조회
     * @param request GetJobListRequest 채용 공고 목록 조회 요청 정보
     * @return List<JobResponse> 비활성 채용 공고 응답 목록
     */
    public List<JobResponse> getDeactivateJobList(GetJobListRequest request) {
        List<Job> jobs =
                jobQueryService.findDeactivateJobList(
                        request.getPage(),
                        request.getSize(),
                        request.getTitle(),
                        request.getDepartment(),
                        request.getEducation(),
                        request.getRecruitType()
                );
        checkJobsIsEmpty(jobs);
        return jobs.stream().map(JobResponse::of).toList();
    }

    /**
     * 채용 공고 수정
     * @param request EditJobRequest 채용 공고 수정 요청 정보
     * @return void
     */
    public void editJob(EditJobRequest request) {
        log.info("[JobUseCase] editJob - idx={}", request.idx());
        jobService.edit(
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

    /**
     * 채용 공고 비활성화
     * @param idx Long 채용 공고 식별자
     * @return void
     */
    public void deactivateJob(Long idx) {
        log.info("[JobUseCase] deactivateJob - idx={}", idx);
        jobService.deactivate(idx);
    }

    /**
     * 채용 공고 삭제
     * @param idx Long 채용 공고 식별자
     * @return void
     */
    public void deleteJob(Long idx) {
        log.info("[JobUseCase] deleteJob - idx={}", idx);
        Job job = jobQueryService.find(idx);
        jobService.delete(job.idx());
    }

    /**
     * 채용 공고 목록 비어있는지 검증
     * @param jobs List<Job> 채용 공고 도메인 객체 목록
     * @return void
     */
    private void checkJobsIsEmpty(List<Job> jobs) {
        if (jobs.isEmpty()) {
            throw JobIsEmptyException.EXCEPTION;
        }
    }

}
