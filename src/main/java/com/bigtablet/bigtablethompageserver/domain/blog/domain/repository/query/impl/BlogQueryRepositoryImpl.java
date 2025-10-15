package com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.impl;

import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.BlogQueryRepository;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.QBlogEntity.blogEntity;

@Repository
@RequiredArgsConstructor
public class BlogQueryRepositoryImpl  implements BlogQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Blog> findAll(PageRequest request) {
        return jpaQueryFactory
                .select(blogConstructorExpression())
                .from(blogEntity)
                .offset((long) (request.page() - 1) * request.size())
                .limit(request.size())
                .orderBy(blogEntity.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Blog> findAllByTitle(PageRequest request, String title) {
        return jpaQueryFactory
                .select(blogConstructorExpression())
                .from(blogEntity)
                .where(blogEntity.titleKr.contains(title).or(blogEntity.titleEn.contains(title)))
                .offset((long) (request.page() - 1) * request.size())
                .limit(request.size())
                .orderBy(blogEntity.createdAt.desc())
                .fetch();
    }

    private ConstructorExpression<Blog> blogConstructorExpression() {
        return Projections.constructor(Blog.class,
                blogEntity.idx,
                blogEntity.titleKr,
                blogEntity.titleEn,
                blogEntity.contentKr,
                blogEntity.contentEn,
                blogEntity.imageUrl,
                blogEntity.views
        );
    }

}
