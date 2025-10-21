package com.bigtablet.bigtablethompageserver.domain.blog.application.service;

import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.EditBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;

public interface BlogService {

    void saveBlog(RegisterBlogRequest request);

    Blog getBlog(Long idx);

    void editBlog(EditBlogRequest request);

    void addViews(Long idx);

    void deleteBlog(Long idx);

}
