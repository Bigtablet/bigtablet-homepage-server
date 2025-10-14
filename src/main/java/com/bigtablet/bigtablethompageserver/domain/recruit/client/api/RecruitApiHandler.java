package com.bigtablet.bigtablethompageserver.domain.recruit.client.api;

import com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase.RecruitUseCase;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitApiHandler {

    private final RecruitUseCase recruitUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerRecruit(@RequestBody @Valid final RegisterRecruitRequest registerRecruitRequest) {
        recruitUseCase.registerRecruit(registerRecruitRequest);
        return BaseResponse.created("등록 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<Recruit> getRecruit(@RequestParam @NotNull final Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                recruitUseCase.getRecruit(idx));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<Recruit>> getAllRecruit() {
        return BaseResponseData.ok(
                "조회 성공",
                recruitUseCase.getAllRecruit());
    }

    @GetMapping("/list/job")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<Recruit>> getAllRecruitByJobId(@RequestParam @NotNull final Long jobId) {
        return BaseResponseData.ok(
                "조회 성공",
                recruitUseCase.getAllRecruitByJobId(jobId));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse editStatus(
            @RequestParam @NotNull
            final Status status,
            @RequestParam @NotNull
            final Long idx
    ) {
        recruitUseCase.editStatus(status, idx);
        return BaseResponse.ok("수정 성공");
    }

    @PatchMapping("/accept")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse acceptRecruit(@RequestParam @NotNull final Long idx) {
        recruitUseCase.acceptRecruit(idx);
        return BaseResponse.ok("수정 성공");
    }

    @PatchMapping("/reject")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse rejectRecruit(@RequestParam @NotNull final Long idx) {
        recruitUseCase.rejectRecruit(idx);
        return BaseResponse.ok("수정 성공");
    }

}
