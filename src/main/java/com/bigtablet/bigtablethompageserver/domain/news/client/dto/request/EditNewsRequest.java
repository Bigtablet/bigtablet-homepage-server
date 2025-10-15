package com.bigtablet.bigtablethompageserver.domain.news.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditNewsRequest(
        @NotNull
        Long idx,
        @NotBlank
        String titleKr,
        @NotBlank
        String titleEn,
        @NotBlank
        String newsUrl
) {}
