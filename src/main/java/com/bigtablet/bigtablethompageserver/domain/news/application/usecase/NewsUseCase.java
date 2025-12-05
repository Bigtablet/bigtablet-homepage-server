package com.bigtablet.bigtablethompageserver.domain.news.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.news.application.query.NewsQueryService;
import com.bigtablet.bigtablethompageserver.domain.news.application.response.NewsResponse;
import com.bigtablet.bigtablethompageserver.domain.news.application.service.NewsService;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.EditNewsRequest;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.exception.NewsIsEmptyException;
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
        newsService.save(
                request.titleKr(),
                request.titleEn(),
                request.newsUrl()
        );
    }

    public NewsResponse getNews(Long idx) {
        News news = newsService.findById(idx);
        return NewsResponse.of(news);
    }

    public List<NewsResponse> getAllNewsList(PageRequest request) {
        List<News> newsList = newsQueryService.getAllNewsList(request.getPage(), request.getSize());
        checkNewsListIsEmpty(newsList);
        return newsList.stream()
                .map(NewsResponse::of)
                .toList();
    }

    public void editNews(EditNewsRequest request) {
        newsService.update(
                request.idx(),
                request.titleKr(),
                request.titleEn(),
                request.newsUrl()
        );
    }

    public void deleteNews(Long idx) {
        newsService.delete(idx);
    }

    public void checkNewsListIsEmpty(List<News> newsList) {
        if (newsList.isEmpty()) {
            throw NewsIsEmptyException.EXCEPTION;
        }
    }

}
