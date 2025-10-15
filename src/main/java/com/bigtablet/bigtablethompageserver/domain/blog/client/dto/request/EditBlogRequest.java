package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditBlogRequest(
        @NotNull
        Long idx,
        @NotBlank
        String titleKr,
        @NotBlank
        String titleEn,
        @NotBlank
        String contentKr,
        @NotBlank
        String contentEn,
        @NotBlank
        String imageUrl
) {}
