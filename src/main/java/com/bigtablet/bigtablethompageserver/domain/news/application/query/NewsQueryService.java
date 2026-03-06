package com.bigtablet.bigtablethompageserver.domain.news.application.query;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa.NewsJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsQueryService {

    private final NewsJpaRepository newsJpaRepository;
    private final NewsQueryRepository newsQueryRepository;

    public News find(Long idx) {
        NewsEntity entity = newsJpaRepository
                .findByIdx(idx)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
        return News.of(entity);
    }

    public List<News> findAll(int page, int size) {
        return newsQueryRepository.findAll(page, size);
    }

}
