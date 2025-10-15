package com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface BlogQueryRepository {
    List<Blog> findAll(PageRequest request);

    List<Blog> findAllByTitle(PageRequest request, String title);
}
