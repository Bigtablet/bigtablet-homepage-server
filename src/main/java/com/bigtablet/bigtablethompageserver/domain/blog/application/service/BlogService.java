package com.bigtablet.bigtablethompageserver.domain.blog.application.service;

import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.EditBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import jakarta.transaction.Transactional;

public interface BlogService {

    void saveBlog(RegisterBlogRequest request);

    Blog getBlog(Long idx);

    @Transactional
    void editBlog(EditBlogRequest request);

    @Transactional
    void addViews(Long idx);

    @Transactional
    void deleteBlog(Long idx);
}
