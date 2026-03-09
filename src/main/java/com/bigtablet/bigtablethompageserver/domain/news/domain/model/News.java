package com.bigtablet.bigtablethompageserver.domain.news.domain.model;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record News(
        Long idx,
        String titleKr,
        String titleEn,
        String newsUrl,
        String thumbnailImageUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static News of(NewsEntity entity) {
        return News.builder()
                .idx(entity.getIdx())
                .titleKr(entity.getTitleKr())
                .titleEn(entity.getTitleEn())
                .newsUrl(entity.getNewsUrl())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
