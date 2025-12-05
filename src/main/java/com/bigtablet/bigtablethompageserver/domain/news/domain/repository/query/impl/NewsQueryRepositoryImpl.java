package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.impl;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query.NewsQueryRepository;
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
    public List<News> findAll(int page, int size) {
        return jpaQueryFactory
                .selectFrom(newsEntity)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(newsEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(News::of)
                .toList();
    }

}
