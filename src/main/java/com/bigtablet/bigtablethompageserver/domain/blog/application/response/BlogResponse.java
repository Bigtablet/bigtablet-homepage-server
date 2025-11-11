package com.bigtablet.bigtablethompageserver.domain.blog.application.response;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BlogResponse(
        Long idx,
        String titleKr,
        String titleEn,
        String contentKr,
        String contentEn,
        String imageUrl,
        int views,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static BlogResponse of(Blog blog) {
        return BlogResponse.builder()
                .idx(blog.idx())
                .titleKr(blog.titleKr())
                .titleEn(blog.titleEn())
                .contentKr(blog.contentKr())
                .contentEn(blog.contentEn())
                .imageUrl(blog.imageUrl())
                .views(blog.views())
                .createdAt(blog.createdAt())
                .modifiedAt(blog.modifiedAt())
                .build();
    }
}
