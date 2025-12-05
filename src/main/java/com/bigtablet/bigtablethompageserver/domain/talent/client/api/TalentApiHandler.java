package com.bigtablet.bigtablethompageserver.domain.talent.client.api;

import com.bigtablet.bigtablethompageserver.domain.talent.application.usecase.TalentUseCase;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SendEmailToTalentRequest;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

}
