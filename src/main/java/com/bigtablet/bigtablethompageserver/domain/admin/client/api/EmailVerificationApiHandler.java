package com.bigtablet.bigtablethompageserver.domain.admin.client.api;

import com.bigtablet.bigtablethompageserver.domain.admin.application.service.EmailVerificationService;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.EmailSendRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.EmailVerifyRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.InvalidEmailDomainException;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
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
@RequestMapping("/admin/email-verification")
public class EmailVerificationApiHandler {

    private static final String ALLOWED_EMAIL_DOMAIN = "bigtablet.com";

    private final EmailVerificationService emailVerificationService;

    /**
     * 어드민 이메일에 OTP 발송 API (3분 TTL)
     * @param request EmailSendRequest 이메일 발송 요청
     * @return BaseResponse 발송 완료 응답
     */
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse send(@RequestBody @Valid final EmailSendRequest request) {
        validateEmailDomain(request.email());
        emailVerificationService.sendCode(request.email());
        return BaseResponse.ok("OTP 발송 성공");
    }

    /**
     * OTP 검증 후 인증 완료 플래그 발행 API (30분 TTL)
     * @param request EmailVerifyRequest 인증 코드 검증 요청
     * @return BaseResponse 검증 완료 응답
     */
    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse verify(@RequestBody @Valid final EmailVerifyRequest request) {
        validateEmailDomain(request.email());
        emailVerificationService.verifyCode(request.email(), request.code());
        return BaseResponse.ok("이메일 인증 성공");
    }

    // 이메일 도메인 정합성 검증 (@bigtablet.com만 허용, 서브도메인 차단)
    private void validateEmailDomain(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2 || !parts[1].equalsIgnoreCase(ALLOWED_EMAIL_DOMAIN)) {
            throw InvalidEmailDomainException.EXCEPTION;
        }
    }

}
