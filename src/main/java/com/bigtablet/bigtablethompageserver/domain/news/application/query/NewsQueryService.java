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

    /**
     * ID로 뉴스 조회
     * @param idx Long 뉴스 ID
     * @return News 뉴스 도메인 객체
     */
    public News find(Long idx) {
        NewsEntity entity = newsJpaRepository
                .findById(idx)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
        return News.of(entity);
    }

    /**
     * 뉴스 목록 페이지 조회
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @return List<News> 뉴스 도메인 객체 목록
     */
    public List<News> findAll(int page, int size) {
        return newsQueryRepository.findAll(page, size);
    }

}
