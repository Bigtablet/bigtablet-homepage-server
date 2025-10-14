package com.bigtablet.bigtablethompageserver.domain.job.client.api;

import com.bigtablet.bigtablethompageserver.domain.job.application.usecase.JobUseCase;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.RegisterJobRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
@Tag(name = "채용공고", description = "채용공고 API")
public class JobApiHandler {

    public final JobUseCase jobUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "채용공고 등록", description = "채용공고를 등록합니다")
    public BaseResponse registerJob(RegisterJobRequest request) {
        jobUseCase.registerJob(request);
        return BaseResponse.created("등록 성공");
    }

}
