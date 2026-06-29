package com.bigtablet.bigtablethompageserver.domain.news.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record EditNewsRequest(
        @NotNull
        Long idx,
        @NotBlank
        String titleKr,
        @NotBlank
        String titleEn,
        @NotBlank
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String newsUrl,
        @NotBlank
        String thumbnailImageUrl
) {}
