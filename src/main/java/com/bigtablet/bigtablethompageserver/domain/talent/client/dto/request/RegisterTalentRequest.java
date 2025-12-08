package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record RegisterTalentRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String name,
        @NotBlank
        String department,
        @URL
        @NotBlank
        String portfolioUrl,
        @URL
        List<String> etcUrl
) {}
