package com.bigtablet.bigtablethompageserver.domain.job.client.api;

import com.bigtablet.bigtablethompageserver.domain.job.application.response.JobResponse;
import com.bigtablet.bigtablethompageserver.domain.job.application.usecase.JobUseCase;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestApiHandler("/job")
public class JobApiHandler {

    private final JobUseCase jobUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerJob(@RequestBody @Valid final RegisterJobRequest request) {
        jobUseCase.registerJob(request);
        return BaseResponse.created("등록 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<JobResponse> getJob(@RequestParam @NotNull final Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                jobUseCase.getJob(idx)
        );
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> getAllJob() {
        return BaseResponseData.ok(
                "조회 성공",
                jobUseCase.getAllJob()
        );
    }

    @GetMapping("/search/{title}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> searchJobByTitle(@PathVariable("title") @NotBlank final String title) {
        return BaseResponseData.ok(
                "검색 성공",
                jobUseCase.searchJobByTitle(title)
        );
    }

    @GetMapping("/search/{department}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> searchJobByDepartment(@PathVariable("department") @NotNull final Department department) {
        return BaseResponseData.ok(
                "검색 성공",
                jobUseCase.searchJobByDepartment(department)
        );
    }

    @GetMapping("/search/{education}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> searchJobByEducation(@PathVariable("education") @NotNull final Education education) {
        return BaseResponseData.ok(
                "검색 성공",
                jobUseCase.searchJobByEducation(education)
        );
    }

    @GetMapping("/search/{recruitType}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> searchJobByRecruitType(@PathVariable("recruitType") @NotNull final RecruitType recruitType) {
        return BaseResponseData.ok(
                "검색 성공",
                jobUseCase.searchJobByRecruitType(recruitType)
        );
    }

    @GetMapping("/false")
    public BaseResponseData<List<JobResponse>> getAllJobIsFalse() {
        return BaseResponseData.ok(
                "조회 성공",
                jobUseCase.getAllJobIsFalse()
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse editJob(@RequestBody @Valid final EditJobRequest request) {
        jobUseCase.editJob(request);
        return BaseResponse.ok("수정 성공");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteJob(@RequestParam @NotNull final Long idx) {
        jobUseCase.deleteJob(idx);
        return BaseResponse.ok("삭제 성공");
    }

}
