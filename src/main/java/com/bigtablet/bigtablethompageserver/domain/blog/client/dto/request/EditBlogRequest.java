package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

import java.time.LocalDateTime;

public record EditBlogRequest(
        Long idx,
        String titleKr,
        String titleEn,
        String contentKr,
        String contentEn,
        String imageUrl,
        LocalDateTime createdAt
) {}
