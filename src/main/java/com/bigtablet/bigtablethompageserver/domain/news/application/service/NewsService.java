package com.bigtablet.bigtablethompageserver.domain.news.application.service;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa.NewsJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsJpaRepository newsJpaRepository;

    /**
     * 뉴스 저장
     * @param titleKr String 한국어 제목
     * @param titleEn String 영어 제목
     * @param newsUrl String 뉴스 URL
     * @param thumbnailImageUrl String 썸네일 이미지 URL
     * @return void
     */
    @Transactional
    public void save(String titleKr, String titleEn, String newsUrl, String thumbnailImageUrl) {
        log.info("[NewsService] save - titleKr={}", titleKr);
        newsJpaRepository.save(NewsEntity.builder()
                .titleKr(titleKr)
                .titleEn(titleEn)
                .newsUrl(newsUrl)
                .thumbnailImageUrl(thumbnailImageUrl)
                .build());
    }

    /**
     * 뉴스 수정
     * @param idx Long 뉴스 ID
     * @param titleKr String 한국어 제목
     * @param titleEn String 영어 제목
     * @param newsUrl String 뉴스 URL
     * @param thumbnailImageUrl String 썸네일 이미지 URL
     * @return void
     */
    @Transactional
    public void edit(Long idx, String titleKr, String titleEn, String newsUrl, String thumbnailImageUrl) {
        log.info("[NewsService] edit - idx={}", idx);
        NewsEntity entity = getNewsEntity(idx);
        entity.editNews(titleKr, titleEn, newsUrl, thumbnailImageUrl);
    }

    /**
     * 뉴스 삭제
     * @param idx Long 뉴스 ID
     * @return void
     */
    @Transactional
    public void delete(Long idx) {
        log.info("[NewsService] delete - idx={}", idx);
        NewsEntity entity = getNewsEntity(idx);
        newsJpaRepository.deleteById(entity.getIdx());
    }

    /**
     * ID로 NewsEntity 조회 (내부용)
     * @param idx Long 뉴스 ID
     * @return NewsEntity 뉴스 엔티티
     */
    private NewsEntity getNewsEntity(Long idx) {
        return newsJpaRepository
                .findById(idx)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
    }

}
