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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsUseCase {

    private final NewsService newsService;
    private final NewsQueryService newsQueryService;

    public void registerNews(RegisterNewsRequest request) {
        log.info("[NewsUseCase] registerNews - titleKr={}", request.titleKr());
        newsService.save(
                request.titleKr(),
                request.titleEn(),
                request.newsUrl(),
                request.thumbnailImageUrl()
        );
    }

    public NewsResponse getNews(Long idx) {
        log.info("[NewsUseCase] getNews - idx={}", idx);
        News news = newsQueryService.find(idx);
        return NewsResponse.of(news);
    }

    public List<NewsResponse> getAllNewsList(PageRequest request) {
        log.info("[NewsUseCase] getAllNewsList - page={}, size={}", request.getPage(), request.getSize());
        List<News> newsList = newsQueryService.findAll(request.getPage(), request.getSize());
        checkNewsListIsEmpty(newsList);
        return newsList.stream()
                .map(NewsResponse::of)
                .toList();
    }

    public void editNews(EditNewsRequest request) {
        log.info("[NewsUseCase] editNews - idx={}", request.idx());
        newsService.edit(
                request.idx(),
                request.titleKr(),
                request.titleEn(),
                request.newsUrl(),
                request.thumbnailImageUrl()
        );
    }

    public void deleteNews(Long idx) {
        log.info("[NewsUseCase] deleteNews - idx={}", idx);
        newsService.delete(idx);
    }

    private void checkNewsListIsEmpty(List<News> newsList) {
        if (newsList.isEmpty()) {
            throw NewsIsEmptyException.EXCEPTION;
        }
    }

}
