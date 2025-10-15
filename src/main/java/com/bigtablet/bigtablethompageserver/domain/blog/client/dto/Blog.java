package com.bigtablet.bigtablethompageserver.domain.blog.client.dto;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;

import java.time.LocalDateTime;

public record Blog(
        Long idx,
        String titleKr,
        String titleEn,
        String contentKr,
        String contentEn,
        String imageUrl,
        int views,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Blog toBlog(BlogEntity entity) {
        return new Blog(
                entity.getIdx(),
                entity.getTitleKr(),
                entity.getTitleEn(),
                entity.getContentKr(),
                entity.getContentEn(),
                entity.getImageUrl(),
                entity.getViews(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
