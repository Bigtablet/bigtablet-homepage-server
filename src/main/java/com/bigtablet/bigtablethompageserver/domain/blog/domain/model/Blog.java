package com.bigtablet.bigtablethompageserver.domain.blog.domain.model;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
    public static Blog of(BlogEntity entity) {
        return Blog.builder()
                .idx(entity.getIdx())
                .titleKr(entity.getTitleKr())
                .titleEn(entity.getTitleEn())
                .contentKr(entity.getContentKr())
                .contentEn(entity.getContentEn())
                .imageUrl(entity.getImageUrl())
                .views(entity.getViews())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
