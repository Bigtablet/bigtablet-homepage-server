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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String titleKr;

    @Column(nullable = false)
    private String titleEn;

    @Column(nullable = false)
    private String newsUrl;

    @Column(nullable = false)
    private String thumbnailImageUrl;

    public void update(
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
