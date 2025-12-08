package com.bigtablet.bigtablethompageserver.domain.talent.client.api;

import com.bigtablet.bigtablethompageserver.domain.talent.application.response.TalentResponse;
import com.bigtablet.bigtablethompageserver.domain.talent.application.usecase.TalentUseCase;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.GetTalentListRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SearchTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SendEmailToTalentRequest;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestApiHandler("/talent")
public class TalentApiHandler {

    private final TalentUseCase talentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerTalent(@RequestBody @Valid final RegisterTalentRequest request) {
        talentUseCase.registerTalent(request);
        return BaseResponse.created("등록 성공");
    }

    @PostMapping("/offer")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse sendMailToTalent(@RequestBody @Valid final SendEmailToTalentRequest request) {
        talentUseCase.sendMailToTalent(request);
        return BaseResponse.ok("전송 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<TalentResponse> getTalent(@RequestParam @NotNull final Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                talentUseCase.getTalent(idx)
        );
    }

    @GetMapping("/list")
    public BaseResponseData<List<TalentResponse>> getTalentList(@ModelAttribute final GetTalentListRequest request) {
        return BaseResponseData.ok(
                "조회 성공",
                talentUseCase.getTalentList(request)
        );
    }

    @GetMapping("/search")
    public BaseResponseData<List<TalentResponse>> searchTalent(@ModelAttribute final SearchTalentRequest request) {
        return BaseResponseData.ok(
                "검색 성공",
                talentUseCase.searchTalent(request)
        );
    }

}
