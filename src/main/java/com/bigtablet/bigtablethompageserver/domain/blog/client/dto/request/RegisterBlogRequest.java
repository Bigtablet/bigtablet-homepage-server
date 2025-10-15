package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

public record RegisterBlogRequest(
        String titleKr,
        String titleEn,
        String contentKr,
        String contentEn,
        String imageUrl
) {}
