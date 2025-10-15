package com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request;

public record RegisterBlogRequest(
        String title,
        String content,
        String imageUrl
) {}
