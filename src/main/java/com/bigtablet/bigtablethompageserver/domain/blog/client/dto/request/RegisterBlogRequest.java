package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterBlogRequest(
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
