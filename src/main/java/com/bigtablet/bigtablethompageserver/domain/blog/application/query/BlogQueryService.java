package com.bigtablet.bigtablethompageserver.domain.blog.application.query;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa.BlogJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.BlogQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogQueryService {

    private final BlogJpaRepository blogJpaRepository;
    private final BlogQueryRepository blogQueryRepository;

    public Blog find(Long idx) {
        BlogEntity entity = blogJpaRepository
                .findById(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
        return Blog.of(entity);
    }

    public List<Blog> findAll(int page, int size) {
        return blogQueryRepository.findAll(page, size);
    }

    public List<Blog> findAllByTitle(int page, int size, String title) {
        return blogQueryRepository.findAllByTitle(page, size, title);
    }

}
