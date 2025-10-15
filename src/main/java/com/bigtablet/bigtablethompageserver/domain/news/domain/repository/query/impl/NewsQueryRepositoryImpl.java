package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.impl;

import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
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
                .select(newsConstructorExpression())
                .from(newsEntity)
                .offset((long) (request.page() - 1) * request.size())
                .limit(request.size())
                .orderBy(newsEntity.createdAt.desc())
                .fetch();
    }

    private ConstructorExpression<News> newsConstructorExpression() {
        return Projections.constructor(News.class,
                newsEntity.idx,
                newsEntity.titleKr,
                newsEntity.titleEn,
                newsEntity.newsUrl,
                newsEntity.createdAt,
                newsEntity.modifiedAt
        );
    }

}
