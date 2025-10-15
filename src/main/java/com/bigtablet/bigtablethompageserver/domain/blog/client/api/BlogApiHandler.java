package com.bigtablet.bigtablethompageserver.domain.blog.client.api;

import com.bigtablet.bigtablethompageserver.domain.blog.application.usecase.BlogUseCase;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogApiHandler {

    private final BlogUseCase blogUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerBlog(RegisterBlogRequest request) {
        blogUseCase.registerBlog(request);
        return BaseResponse.created("등록 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<Blog> getBlog(Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                blogUseCase.getBlog(idx));
    }

}
