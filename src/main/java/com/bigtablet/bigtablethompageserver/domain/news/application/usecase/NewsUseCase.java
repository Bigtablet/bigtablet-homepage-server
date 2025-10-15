package com.bigtablet.bigtablethompageserver.domain.news.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.news.application.query.NewsQueryService;
import com.bigtablet.bigtablethompageserver.domain.news.application.service.NewsService;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsUseCase {

    private final NewsService newsService;
    private final NewsQueryService newsQueryService;

    public void registerNews(RegisterNewsRequest request) {
        newsService.saveNews(request);
    }

    public List<News> getAllNewsList(PageRequest request) {
        return newsQueryService.getAllNewsList(request);
    }

    public void deleteNews(Long idx) {
        newsService.deleteNews(idx);
    }

}
