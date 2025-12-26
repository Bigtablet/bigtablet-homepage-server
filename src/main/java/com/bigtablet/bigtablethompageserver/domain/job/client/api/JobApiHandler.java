package com.bigtablet.bigtablethompageserver.domain.job.client.api;

import com.bigtablet.bigtablethompageserver.domain.job.application.response.JobResponse;
import com.bigtablet.bigtablethompageserver.domain.job.application.usecase.JobUseCase;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.GetJobListRequest;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public BaseResponseData<List<JobResponse>> getJobList(@ModelAttribute final GetJobListRequest request) {
        return BaseResponseData.ok(
                "조회 성공",
                jobUseCase.getJobList(request)
        );
    }

    @GetMapping("/list/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<JobResponse>> getDeactivateJobList(@ModelAttribute final GetJobListRequest request) {
        return BaseResponseData.ok(
                "조회 성공",
                jobUseCase.getDeactivateJobList(request)
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse editJob(@RequestBody @Valid final EditJobRequest request) {
        jobUseCase.editJob(request);
        return BaseResponse.ok("수정 성공");
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deactivateJob(@RequestParam @NotNull final Long idx) {
        jobUseCase.deactivateJob(idx);
        return BaseResponse.ok("비활성화 성공");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteJob(@RequestParam @NotNull final Long idx) {
        jobUseCase.deleteJob(idx);
        return BaseResponse.ok("삭제 성공");
    }

}
