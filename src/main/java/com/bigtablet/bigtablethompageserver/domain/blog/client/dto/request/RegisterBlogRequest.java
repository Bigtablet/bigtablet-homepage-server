package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterBlogRequest(
        @NotBlank
        @Size(max = 255)
        String titleKr,
        @NotBlank
        @Size(max = 255)
        String titleEn,
        @NotBlank
        @Size(max = 100000)
        String contentKr,
        @NotBlank
        @Size(max = 100000)
        String contentEn,
        @NotBlank
        @Size(max = 255)
        String imageUrl
) {}
