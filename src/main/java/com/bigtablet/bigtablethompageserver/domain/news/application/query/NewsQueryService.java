package com.bigtablet.bigtablethompageserver.domain.news.application.query;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface NewsQueryService {

    List<News> getAllNewsList(PageRequest request);

}
