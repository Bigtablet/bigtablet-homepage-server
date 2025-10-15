package com.bigtablet.bigtablethompageserver.domain.blog.application.query.impl;

import com.bigtablet.bigtablethompageserver.domain.blog.application.query.BlogQueryService;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.BlogQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogQueryServiceImpl implements BlogQueryService {

    private final BlogQueryRepository blogQueryRepository;

    @Override
    public List<Blog> getAllBlogList(PageRequest request) {
        List<Blog> blogs = blogQueryRepository.findAll(request);
        checkListIsEmpty(blogs);
        return blogs;
    }

    @Override
    public List<Blog> searchBlogByTitle(PageRequest request, String title) {
        List<Blog> blogs = blogQueryRepository.findAllByTitle(request, title);
        checkListIsEmpty(blogs);
        return blogs;
    }

    public void checkListIsEmpty(List<Blog> blogList) {
        if (blogList == null || blogList.isEmpty()) {
            throw BlogNotFoundException.EXCEPTION;
        }
    }

}
