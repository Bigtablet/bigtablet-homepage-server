package com.bigtablet.bigtablethompageserver.domain.news.application.query.impl;

import com.bigtablet.bigtablethompageserver.domain.news.application.query.NewsQueryService;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsQueryServiceImpl implements NewsQueryService {

    private final NewsQueryRepository newsQueryRepository;

    @Override
    public List<News> getAllNewsList(PageRequest request) {
        List<News> newsList = newsQueryRepository.findAll(request);
        checkListIsEmpty(newsList);
        return newsList;
    }

    public void checkListIsEmpty(List<News> newsList) {
        if (newsList == null || newsList.isEmpty()) {
            throw NewsNotFoundException.EXCEPTION;
        }
    }

}
