package com.bigtablet.bigtablethompageserver.domain.news.application.response;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NewsResponse(
        Long idx,
        String titleKr,
        String titleEn,
        String newsUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static NewsResponse of(News news) {
        return NewsResponse.builder()
                .idx(news.idx())
                .titleKr(news.titleKr())
                .titleEn(news.titleEn())
                .newsUrl(news.newsUrl())
                .createdAt(news.createdAt())
                .modifiedAt(news.modifiedAt())
                .build();
    }
}
