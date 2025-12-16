package com.bigtablet.bigtablethompageserver.domain.news.application.query;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsQueryService {

    private final NewsQueryRepository newsQueryRepository;

    public List<News> getAllNewsList(int page, int size) {
        List<News> newsList = newsQueryRepository.findAll(page, size);
        return newsList;
    }

}
