package com.bigtablet.bigtablethompageserver.domain.blog.client.dto;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;

public record Blog(
        Long idx,
        String title,
        String content,
        String imageUrl,
        int views
) {
    public static Blog toBlog(BlogEntity entity) {
        return new Blog(
                entity.getIdx(),
                entity.getTitle(),
                entity.getContent(),
                entity.getImageUrl(),
                entity.getViews()
        );
    }
}
