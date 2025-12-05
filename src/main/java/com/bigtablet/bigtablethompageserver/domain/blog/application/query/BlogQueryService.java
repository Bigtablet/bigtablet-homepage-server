package com.bigtablet.bigtablethompageserver.domain.blog.application.query;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.BlogQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogQueryService{

    private final BlogQueryRepository blogQueryRepository;

    public List<Blog> getAllBlogList(int page, int size) {
        List<Blog> blogs = blogQueryRepository.findAll(page, size);
        checkListIsEmpty(blogs);
        return blogs;
    }

    public List<Blog> searchBlogByTitle(int page, int size, String title) {
        List<Blog> blogs = blogQueryRepository.findAllByTitle(page, size, title);
        checkListIsEmpty(blogs);
        return blogs;
    }

    public void checkListIsEmpty(List<Blog> blogList) {
        if (blogList == null || blogList.isEmpty()) {
            throw BlogNotFoundException.EXCEPTION;
        }
    }

}
