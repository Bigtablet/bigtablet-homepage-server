package com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface BlogQueryRepository {

    List<Blog> findAll(int page, int size);

    List<Blog> findAllByTitle(int page, int size, String title);

}
