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

    /**
     * 블로그 등록
     * @param request RegisterBlogRequest 블로그 등록 요청 정보
     * @return void
     */
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

    /**
     * 블로그 단건 조회
     * @param idx Long 블로그 식별자
     * @return BlogResponse 블로그 응답
     */
    public BlogResponse getBlog(Long idx) {
        log.info("[BlogUseCase] getBlog - idx={}", idx);
        Blog blog = blogQueryService.find(idx);
        return BlogResponse.of(blog);
    }

    /**
     * 블로그 목록 페이지 조회
     * @param request PageRequest 페이지 요청 정보
     * @return List<BlogResponse> 블로그 응답 목록
     */
    public List<BlogResponse> getAllBlogList(PageRequest request) {
        log.info("[BlogUseCase] getAllBlogList - page={}, size={}", request.getPage(), request.getSize());
        List<Blog> blogs = blogQueryService.findAll(request.getPage(), request.getSize());
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    /**
     * 제목으로 블로그 검색
     * @param request PageRequest 페이지 요청 정보
     * @param title String 검색할 블로그 제목
     * @return List<BlogResponse> 블로그 응답 목록
     */
    public List<BlogResponse> searchBlogByTitle(PageRequest request, String title) {
        log.info("[BlogUseCase] searchBlogByTitle - title={}", title);
        List<Blog> blogs = blogQueryService.findAllByTitle(request.getPage(), request.getSize(), title);
        checkBlogsIsEmpty(blogs);
        return blogs.stream()
                .map(BlogResponse::of)
                .toList();
    }

    /**
     * 블로그 수정
     * @param request EditBlogRequest 블로그 수정 요청 정보
     * @return void
     */
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

    /**
     * 블로그 삭제
     * @param idx Long 블로그 식별자
     * @return void
     */
    public void deleteBlog(Long idx) {
        log.info("[BlogUseCase] deleteBlog - idx={}", idx);
        blogService.delete(idx);
    }

    /**
     * 블로그 조회수 증가
     * @param idx Long 블로그 식별자
     * @return void
     */
    public void addViews(Long idx) {
        log.info("[BlogUseCase] addViews - idx={}", idx);
        blogService.addViews(idx);
    }

    /**
     * 블로그 목록 비어있는지 검증
     * @param blogs List<Blog> 블로그 도메인 객체 목록
     * @return void
     */
    private void checkBlogsIsEmpty(List<Blog> blogs) {
        if (blogs.isEmpty()) {
            throw BlogIsEmptyException.EXCEPTION;
        }
    }

}
