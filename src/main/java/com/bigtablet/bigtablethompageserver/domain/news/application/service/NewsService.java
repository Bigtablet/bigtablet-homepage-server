package com.bigtablet.bigtablethompageserver.domain.news.application.service;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa.NewsJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsJpaRepository newsJpaRepository;

    public void save(String titleKr, String titleEn, String newsUrl) {
        newsJpaRepository.save(NewsEntity.builder()
                .titleKr(titleKr)
                .titleEn(titleEn)
                .newsUrl(newsUrl)
                .build());
    }

    public News findById(Long idx) {
        NewsEntity entity = getNewsEntity(idx);
        return News.of(entity);
    }

    public List<News> findAll(List<NewsEntity> entities) {
        return entities.stream()
                .map(News::of)
                .toList();
    }

    @Transactional
    public void update(Long idx, String titleKr, String titleEn, String newsUrl) {
        NewsEntity entity = getNewsEntity(idx);
        entity.update(titleKr, titleEn, newsUrl);
    }

    @Transactional
    public void delete(Long idx) {
        NewsEntity entity = getNewsEntity(idx);
        newsJpaRepository.deleteById(entity.getIdx());
    }

    private NewsEntity getNewsEntity(Long idx) {
        return newsJpaRepository
                .findByIdx(idx)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
    }

}
