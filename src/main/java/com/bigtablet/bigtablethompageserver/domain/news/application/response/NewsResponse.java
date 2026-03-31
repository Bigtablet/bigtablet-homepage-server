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
        String thumbnailImageUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    /**
     * News 도메인 객체를 NewsResponse로 변환합니다.
     * @param news News 변환할 도메인 객체
     * @return NewsResponse 변환된 응답 DTO
     */
    public static NewsResponse of(News news) {
        return NewsResponse.builder()
                .idx(news.idx())
                .titleKr(news.titleKr())
                .titleEn(news.titleEn())
                .newsUrl(news.newsUrl())
                .thumbnailImageUrl(news.thumbnailImageUrl())
                .createdAt(news.createdAt())
                .modifiedAt(news.modifiedAt())
                .build();
    }
}
