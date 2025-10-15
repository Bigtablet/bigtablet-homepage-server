package com.bigtablet.bigtablethompageserver.domain.news.application.service.impl;

import com.bigtablet.bigtablethompageserver.domain.news.application.service.NewsService;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa.NewsJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsJpaRepository newsJpaRepository;

    @Override
    public void saveNews(RegisterNewsRequest request) {
        newsJpaRepository.save(NewsEntity.builder()
                .titleKr(request.titleKr())
                .titleEn(request.titleEn())
                .newsUrl(request.newsUrl())
                .build());
    }

    @Override
    @Transactional
    public void deleteNews(Long idx) {
        News news = getNews(idx);
        newsJpaRepository.deleteById(news.idx());
    }

    public News getNews(Long idx) {
        return newsJpaRepository
                .findByIdx(idx)
                .map(News::toNews)
                .orElseThrow(()-> NewsNotFoundException.EXCEPTION);
    }

}
