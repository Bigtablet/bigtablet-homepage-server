package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SendEmailToTalentRequest(
        @NotNull
        Long idx,
        @NotBlank
        @Size(max = 5000)
        String text
) {}
