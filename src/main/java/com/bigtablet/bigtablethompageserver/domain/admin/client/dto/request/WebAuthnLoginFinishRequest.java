package com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record WebAuthnLoginFinishRequest(
        @NotBlank
        @Email(message = "이메일 형식이 틀립니다.")
        String email,
        @NotBlank
        String credential
) {}
