package com.bigtablet.bigtablethompageserver.domain.news.domain.entity;

import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_news")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsEntity extends BaseEntity {

    // 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 한국어 제목
    @Column(nullable = false)
    private String titleKr;

    // 영문 제목
    @Column(nullable = false)
    private String titleEn;

    // 뉴스 URL
    @Column(nullable = false)
    private String newsUrl;

    // 썸네일 이미지 URL
    @Column(nullable = false)
    private String thumbnailImageUrl;

    /**
     * 뉴스 정보를 수정한다
     * @param titleKr String 한국어 제목
     * @param titleEn String 영문 제목
     * @param newsUrl String 뉴스 URL
     * @param thumbnailImageUrl String 썸네일 이미지 URL
     */
    public void editNews(
            String titleKr,
            String titleEn,
            String newsUrl,
            String thumbnailImageUrl
    ) {
        this.titleKr = titleKr;
        this.titleEn = titleEn;
        this.newsUrl = newsUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}
