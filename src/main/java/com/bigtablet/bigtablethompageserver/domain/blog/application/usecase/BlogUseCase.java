package com.bigtablet.bigtablethompageserver.domain.blog.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.blog.application.service.BlogService;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogUseCase {

    private final BlogService blogService;

    public void registerBlog(RegisterBlogRequest request) {
        blogService.saveBlog(request);
    }

    public Blog getBlog(Long idx) {
        return blogService.getBlog(idx);
    }

    public void addViews(Long idx) {
        blogService.addViews(idx);
    }

}
