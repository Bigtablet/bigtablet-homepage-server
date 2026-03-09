package com.bigtablet.bigtablethompageserver.domain.blog.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.blog.application.query.BlogQueryService;
import com.bigtablet.bigtablethompageserver.domain.blog.application.response.BlogResponse;
import com.bigtablet.bigtablethompageserver.domain.blog.application.service.BlogService;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.EditBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogUseCase {

    private final BlogService blogService;
    private final BlogQueryService blogQueryService;

    public void registerBlog(RegisterBlogRequest request) {
        log.info("[BlogUseCase] registerBlog - titleKr={}", request.titleKr());
        blogService.save(
                request.titleKr(),
                request.titleEn(),
                request.contentKr(),
                request.contentEn(),
                request.imageUrl()
        );
    }

    public BlogResponse getBlog(Long idx) {
        log.info("[BlogUseCase] getBlog - idx={}", idx);
        Blog blog = blogQueryService.find(idx);
        return BlogResponse.of(blog);
    }

    public List<BlogResponse> getAllBlogList(PageRequest request) {
        log.info("[BlogUseCase] getAllBlogList - page={}, size={}", request.getPage(), request.getSize());
        List<Blog> blogs = blogQueryService.findAll(request.getPage(), request.getSize());
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    public List<BlogResponse> searchBlogByTitle(PageRequest request, String title) {
        log.info("[BlogUseCase] searchBlogByTitle - title={}", title);
        List<Blog> blogs = blogQueryService.findAllByTitle(request.getPage(), request.getSize(), title);
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    public void editBlog(EditBlogRequest request) {
        log.info("[BlogUseCase] editBlog - idx={}", request.idx());
        blogService.edit(
                request.idx(),
                request.titleKr(),
                request.titleEn(),
                request.contentKr(),
                request.contentEn(),
                request.imageUrl()
        );
    }

    public void deleteBlog(Long idx) {
        log.info("[BlogUseCase] deleteBlog - idx={}", idx);
        blogService.delete(idx);
    }

    public void addViews(Long idx) {
        log.info("[BlogUseCase] addViews - idx={}", idx);
        blogService.addViews(idx);
    }

    private void checkBlogsIsEmpty(List<Blog> blogs) {
        if (blogs.isEmpty()) {
            throw BlogIsEmptyException.EXCEPTION;
        }
    }

}
