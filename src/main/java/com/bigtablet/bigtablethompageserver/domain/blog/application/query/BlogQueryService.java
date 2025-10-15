package com.bigtablet.bigtablethompageserver.domain.blog.application.query;

import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

import java.util.List;

public interface BlogQueryService {
    List<Blog> getAllBlogList(PageRequest request);

    List<Blog> searchBlogByTitle(PageRequest request, String title);
}
