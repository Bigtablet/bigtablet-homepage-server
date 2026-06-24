package com.bigtablet.bigtablethompageserver.domain.admin.client.api;

import com.bigtablet.bigtablethompageserver.domain.admin.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.admin.application.response.WebAuthnOptionsResponse;
import com.bigtablet.bigtablethompageserver.domain.admin.application.usecase.WebAuthnUseCase;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnLoginFinishRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnLoginStartRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnRegisterFinishRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnRegisterStartRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/webauthn")
public class WebAuthnApiHandler {

    private final WebAuthnUseCase webAuthnUseCase;

    /**
     * 물리키 등록 시작 API — 챌린지 발급
     * @param request 등록 시작 요청
     * @return BaseResponseData 등록 옵션 JSON
     */
    @PostMapping("/register/start")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<WebAuthnOptionsResponse> registerStart(
            @RequestBody @Valid final WebAuthnRegisterStartRequest request
    ) {
        return BaseResponseData.ok(
                "등록 챌린지 발급 성공",
                webAuthnUseCase.registerStart(request)
        );
    }

    /**
     * 물리키 등록 완료 API — 크레덴셜 저장
     * @param request 등록 완료 요청
     * @return BaseResponse 등록 완료 응답
     */
    @PostMapping("/register/finish")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerFinish(
            @RequestBody @Valid final WebAuthnRegisterFinishRequest request
    ) {
        webAuthnUseCase.registerFinish(request);
        return BaseResponse.created("물리키 등록 성공");
    }

    /**
     * 물리키 로그인 시작 API — 챌린지 발급
     * @param request 로그인 시작 요청
     * @return BaseResponseData 로그인 옵션 JSON
     */
    @PostMapping("/login/start")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<WebAuthnOptionsResponse> loginStart(
            @RequestBody @Valid final WebAuthnLoginStartRequest request
    ) {
        return BaseResponseData.ok(
                "로그인 챌린지 발급 성공",
                webAuthnUseCase.loginStart(request)
        );
    }

    /**
     * 물리키 로그인 완료 API — JWT 발급
     * @param request 로그인 완료 요청
     * @return BaseResponseData JWT 토큰 응답
     */
    @PostMapping("/login/finish")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<JsonWebTokenResponse> loginFinish(
            @RequestBody @Valid final WebAuthnLoginFinishRequest request
    ) {
        return BaseResponseData.ok(
                "로그인 성공",
                webAuthnUseCase.loginFinish(request)
        );
    }

}
