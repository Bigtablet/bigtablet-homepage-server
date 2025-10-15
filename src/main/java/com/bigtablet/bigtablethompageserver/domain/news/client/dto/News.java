package com.bigtablet.bigtablethompageserver.domain.news.client.dto;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;

import java.time.LocalDateTime;

public record News(
        Long idx,
        String titleKr,
        String titleEn,
        String newsUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static News toNews(NewsEntity entity) {
        return new News(
                entity.getIdx(),
                entity.getTitleKr(),
                entity.getTitleEn(),
                entity.getNewsUrl(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
