package com.bigtablet.bigtablethompageserver.domain.news.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record RegisterNewsRequest(
        @NotBlank
        String titleKr,
        @NotBlank
        String titleEn,
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String newsUrl
) {}
