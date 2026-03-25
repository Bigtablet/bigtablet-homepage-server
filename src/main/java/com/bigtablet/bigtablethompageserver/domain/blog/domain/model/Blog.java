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
    /**
     * BlogEntity를 Blog 도메인 객체로 변환합니다.
     * @param entity BlogEntity 변환할 엔티티
     * @return Blog 변환된 도메인 객체
     */
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
