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

    @Transactional
    public void save(String titleKr, String titleEn, String newsUrl) {
        log.info("[NewsService] save - titleKr={}", titleKr);
        newsJpaRepository.save(NewsEntity.builder()
                .titleKr(titleKr)
                .titleEn(titleEn)
                .newsUrl(newsUrl)
                .build());
    }

    @Transactional
    public void edit(Long idx, String titleKr, String titleEn, String newsUrl) {
        log.info("[NewsService] edit - idx={}", idx);
        NewsEntity entity = getNewsEntity(idx);
        entity.editNews(titleKr, titleEn, newsUrl);
    }

    @Transactional
    public void delete(Long idx) {
        log.info("[NewsService] delete - idx={}", idx);
        NewsEntity entity = getNewsEntity(idx);
        newsJpaRepository.deleteById(entity.getIdx());
    }

    private NewsEntity getNewsEntity(Long idx) {
        return newsJpaRepository
                .findByIdx(idx)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
    }

}
