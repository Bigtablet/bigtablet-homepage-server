package com.bigtablet.bigtablethompageserver.domain.news.client.api;

import com.bigtablet.bigtablethompageserver.domain.news.application.response.NewsResponse;
import com.bigtablet.bigtablethompageserver.domain.news.application.usecase.NewsUseCase;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.EditNewsRequest;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import com.bigtablet.bigtablethompageserver.global.common.annotation.RestApiHandler;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestApiHandler("/news")
public class NewsApiHandler {

    private final NewsUseCase newsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerNews(@RequestBody @Valid final RegisterNewsRequest request) {
        newsUseCase.registerNews(request);
        return BaseResponse.created("등록 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<NewsResponse> getNews(@RequestParam @NotNull final Long idx) {
        return BaseResponseData.ok(
                "조회 성공",
                newsUseCase.getNews(idx)
        );
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseData<List<NewsResponse>> getAllNewsList(@ModelAttribute @Valid final PageRequest request) {
        return BaseResponseData.ok(
                "조회 성공",
                newsUseCase.getAllNewsList(request)
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse editNews(@RequestBody @Valid final EditNewsRequest request) {
        newsUseCase.editNews(request);
        return BaseResponse.ok("수정 성공");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteNews(@RequestParam @NotNull final Long idx) {
        newsUseCase.deleteNews(idx);
        return BaseResponse.ok("삭제 성공");
    }

}
