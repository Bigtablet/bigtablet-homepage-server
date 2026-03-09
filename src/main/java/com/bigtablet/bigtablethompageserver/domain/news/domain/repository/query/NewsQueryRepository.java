package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.news.domain.entity.QNewsEntity.newsEntity;

@Repository
@RequiredArgsConstructor
public class NewsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

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
