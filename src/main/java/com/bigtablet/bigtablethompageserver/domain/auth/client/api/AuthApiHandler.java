package com.bigtablet.bigtablethompageserver.domain.auth.client.api;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.usecase.AuthUseCase;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.EmailCheckRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.EmailSendRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.RefreshTokenRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignInRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignUpRequest;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@RestApiHandler("/auth")
public class AuthApiHandler {

    private final AuthUseCase authUseCase;

    /**
     * 회원가입 API
     *
     * @param request SignUpRequest
     *
     * @return status, message
     *
     * */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse signUp(@RequestBody @Valid final SignUpRequest request) {
        authUseCase.signUp(request);
        return BaseResponse.created("회원가입 성공");
    }

    /**
     * 로그인 API
     *
     * @param request email, password
     *
     * @return status, message, data { JsonWebTokenResponse }
     *
     * */
    @PostMapping("/sign-in")
    public BaseResponseData<JsonWebTokenResponse> signIn(@RequestBody @Valid final SignInRequest request) {
        return BaseResponseData.ok(
                "로그인 성공",
                authUseCase.signIn(request)
        );
    }

    /**
     * 토큰 재발급 API
     *
     * @param request RefreshTokenRequest
     *
     * @return status, message, data { RefreshTokenResponse }
     *
     * */
    @PostMapping("/refresh")
    public BaseResponseData<RefreshTokenResponse> refresh(@RequestBody @Valid final RefreshTokenRequest request) {
        return BaseResponseData.ok(
                "재발급 성공",
                authUseCase.refresh(request)
        );
    }

    /**
     * 이메일 전송 API
     *
     * @param request email
     *
     * @return status, message
     *
     * */
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse sendEmail(@RequestBody @Valid final EmailSendRequest request) {
        authUseCase.sendVerificationEmail(request.email());
        return BaseResponse.ok("이메일 전송 성공");
    }

    /**
     * 인증번호 확인 API
     *
     * @param request email, authCode
     *
     * @return status, message
     *
     * */
    @PostMapping("/email/check")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse checkEmail(@RequestBody @Valid final EmailCheckRequest request) {
        authUseCase.checkAuthCode(request.email(), request.authCode());
        return BaseResponse.ok("인증 완료");
    }

}
