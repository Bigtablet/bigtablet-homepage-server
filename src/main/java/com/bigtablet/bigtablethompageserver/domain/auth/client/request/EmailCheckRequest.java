package com.bigtablet.bigtablethompageserver.domain.auth.client.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EmailCheckRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        String email,
        @NotBlank(message = "인증번호는 필수입니다.")
        @Length(min = 6, max = 6)
        String authCode
){}