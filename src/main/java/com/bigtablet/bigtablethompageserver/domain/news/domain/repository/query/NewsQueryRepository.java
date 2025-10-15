package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface NewsQueryRepository {

    List<News> findAll(PageRequest request);

}
