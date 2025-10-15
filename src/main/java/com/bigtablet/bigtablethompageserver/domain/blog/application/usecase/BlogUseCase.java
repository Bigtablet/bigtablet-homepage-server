package com.bigtablet.bigtablethompageserver.domain.blog.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.blog.application.query.BlogQueryService;
import com.bigtablet.bigtablethompageserver.domain.blog.application.service.BlogService;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogUseCase {

    private final BlogService blogService;
    private final BlogQueryService blogQueryService;

    public void registerBlog(RegisterBlogRequest request) {
        blogService.saveBlog(request);
    }

    public Blog getBlog(Long idx) {
        return blogService.getBlog(idx);
    }

    public List<Blog> getAllBlogList(PageRequest pageRequest) {
        return blogQueryService.getAllBlogList(pageRequest);
    }

    public List<Blog> searchBlogByTitle(PageRequest request, String title) {
        return blogQueryService.searchBlogByTitle(request, title);
    }

    public void addViews(Long idx) {
        blogService.addViews(idx);
    }

}
