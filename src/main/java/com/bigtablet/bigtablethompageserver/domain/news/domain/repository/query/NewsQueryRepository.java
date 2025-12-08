package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.news.domain.model.News;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface NewsQueryRepository {

    List<News> findAll(int page, int size);

}
