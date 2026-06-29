package com.bigtablet.bigtablethompageserver.domain.news.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record EditNewsRequest(
        @NotNull
        Long idx,
        @NotBlank
        @Size(max = 255)
        String titleKr,
        @NotBlank
        @Size(max = 255)
        String titleEn,
        @NotBlank
        @Size(max = 255)
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String newsUrl,
        @NotBlank
        @Size(max = 255)
        String thumbnailImageUrl
) {}
