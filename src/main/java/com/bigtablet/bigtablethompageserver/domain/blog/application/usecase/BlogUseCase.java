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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogUseCase {

    private final BlogService blogService;
    private final BlogQueryService blogQueryService;

    public void registerBlog(RegisterBlogRequest request) {
        blogService.saveBlog(
                request.titleKr(),
                request.titleEn(),
                request.contentKr(),
                request.contentEn(),
                request.imageUrl()
        );
    }

    public BlogResponse getBlog(Long idx) {
        Blog blog = blogService.findById(idx);
        return BlogResponse.of(blog);
    }

    public List<BlogResponse> getAllBlogList(PageRequest request) {
        List<Blog> blogs = blogQueryService.getAllBlogList(request.getPage(), request.getSize());
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    public List<BlogResponse> searchBlogByTitle(PageRequest request, String title) {
        List<Blog> blogs = blogQueryService.searchBlogByTitle(request.getPage(), request.getSize(), title);
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    public void editBlog(EditBlogRequest request) {
        blogService.editBlog(
                request.idx(),
                request.titleKr(),
                request.titleEn(),
                request.contentKr(),
                request.contentEn(),
                request.imageUrl()
        );
    }

    public void deleteBlog(Long idx) {
        blogService.deleteBlog(idx);
    }

    public void addViews(Long idx) {
        blogService.addViews(idx);
    }

    public void checkBlogsIsEmpty(List<Blog> blogs) {
        if (blogs.isEmpty()) {
            throw BlogIsEmptyException.EXCEPTION;
        }
    }

}
