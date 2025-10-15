package com.bigtablet.bigtablethompageserver.domain.blog.client.dto;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;

public record Blog(
        Long idx,
        String titleKr,
        String titleEn,
        String contentKr,
        String contentEn,
        String imageUrl,
        int views
) {
    public static Blog toBlog(BlogEntity entity) {
        return new Blog(
                entity.getIdx(),
                entity.getTitleKr(),
                entity.getTitleEn(),
                entity.getContentKr(),
                entity.getContentEn(),
                entity.getImageUrl(),
                entity.getViews()
        );
    }
}
