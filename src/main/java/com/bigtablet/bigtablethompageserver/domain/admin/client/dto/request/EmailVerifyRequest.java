package com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmailVerifyRequest(
        @NotBlank
        @Email(message = "이메일 형식이 틀립니다.")
        String email,
        @NotBlank
        @Pattern(regexp = "^\\d{6}$", message = "6자리 숫자 OTP가 필요합니다.")
        String code
) {}
