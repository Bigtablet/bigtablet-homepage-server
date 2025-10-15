package com.bigtablet.bigtablethompageserver.domain.blog.client.api;

import com.bigtablet.bigtablethompageserver.domain.blog.application.usecase.BlogUseCase;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.EditBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogApiHandler {

    private final BlogUseCase blogUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerBlog(@RequestBody @Valid final RegisterBlogRequest request) {
        blogUseCase.registerBlog(request);
        return BaseResponse.created("등록 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<Blog> getBlog(@RequestParam @NotNull final Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                blogUseCase.getBlog(idx)
        );
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<Blog>> getAllBlogList(@ModelAttribute @Valid final PageRequest request) {
        return BaseResponseData.ok(
                "조회 성공",
                blogUseCase.getAllBlogList(request)
        );
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<Blog>> searchBlogByTitle(
            @ModelAttribute
            final PageRequest request,
            @RequestParam @NotBlank
            final String title
    ) {
        return BaseResponseData.ok(
                "검색 성공",
                blogUseCase.searchBlogByTitle(request, title)
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse editBlog(@RequestBody @Valid final EditBlogRequest request) {
        blogUseCase.editBlog(request);
        return BaseResponse.ok("수정 성공");
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse addViews(@RequestParam @NotNull final Long idx) {
        blogUseCase.addViews(idx);
        return BaseResponse.ok("수정 성공");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteBlog(@RequestParam @NotNull final Long idx) {
        blogUseCase.deleteBlog(idx);
        return BaseResponse.ok("삭제 성공");
    }

}
