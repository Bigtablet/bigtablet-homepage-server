package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.impl;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.news.domain.entity.QNewsEntity.newsEntity;

@Repository
@RequiredArgsConstructor
public class NewsQueryRepositoryImpl implements NewsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<News> findAll(PageRequest request) {
        return jpaQueryFactory
                .selectFrom(newsEntity)
                .offset((long) (request.getPage() - 1) * request.getPage())
                .limit(request.getPage())
                .orderBy(newsEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(News::of)
                .toList();
    }

}
