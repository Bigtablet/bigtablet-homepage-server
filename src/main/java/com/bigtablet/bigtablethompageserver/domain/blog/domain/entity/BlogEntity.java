package com.bigtablet.bigtablethompageserver.domain.blog.domain.entity;

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
@Table(name = "tb_blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogEntity extends BaseEntity {

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

    // 한국어 내용
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contentKr;

    // 영문 내용
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contentEn;

    // 대표 이미지 URL
    @Column(nullable = false)
    private String imageUrl;

    // 조회수
    private int views;

    /**
     * 조회수를 1 증가시킨다
     */
    public void addViews() {
        this.views++;
    }

    /**
     * 블로그 정보를 수정한다
     * @param titleKr String 한국어 제목
     * @param titleEn String 영문 제목
     * @param contentKr String 한국어 내용
     * @param contentEn String 영문 내용
     * @param imageUrl String 대표 이미지 URL
     */
    public void editBlog(
            String titleKr,
            String titleEn,
            String contentKr,
            String contentEn,
            String imageUrl
    ) {
        this.titleKr = titleKr;
        this.titleEn = titleEn;
        this.contentKr = contentKr;
        this.contentEn = contentEn;
        this.imageUrl = imageUrl;
    }

}
