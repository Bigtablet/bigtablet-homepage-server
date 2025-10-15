package com.bigtablet.bigtablethompageserver.domain.news.client.dto.request;

public record EditNewsRequest(
        Long idx,
        String titleKr,
        String titleEn,
        String newsUrl
) {}
