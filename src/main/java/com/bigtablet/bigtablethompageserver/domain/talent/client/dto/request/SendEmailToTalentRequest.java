package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import jakarta.validation.constraints.NotNull;

public record SendEmailToTalentRequest(
        @NotNull
        Long idx,
        @NotNull
        String text
) {}
