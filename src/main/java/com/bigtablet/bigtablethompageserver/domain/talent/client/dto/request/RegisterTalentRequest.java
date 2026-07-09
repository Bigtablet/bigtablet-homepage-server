package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record RegisterTalentRequest(
        @Email
        @NotBlank
        @Size(max = 255)
        String email,
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @Size(max = 255)
        String department,
        @URL
        @NotBlank
        @Size(max = 255)
        String portfolioUrl,
        @Size(max = 20)
        List<@URL @Size(max = 255) String> etcUrl
) {}
