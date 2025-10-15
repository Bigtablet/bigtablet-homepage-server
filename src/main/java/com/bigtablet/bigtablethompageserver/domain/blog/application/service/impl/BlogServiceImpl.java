package com.bigtablet.bigtablethompageserver.domain.blog.application.service.impl;

import com.bigtablet.bigtablethompageserver.domain.blog.application.service.BlogService;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.EditBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.client.dto.request.RegisterBlogRequest;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa.BlogJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogJpaRepository blogJpaRepository;

    @Override
    public void saveBlog(RegisterBlogRequest request) {
        blogJpaRepository.save(BlogEntity.builder()
                .titleKr(request.titleKr())
                .titleEn(request.titleEn())
                .contentKr(request.contentKr())
                .contentEn(request.contentEn())
                .imageUrl(request.imageUrl())
                .views(0)
                .build());
    }

    @Override
    public Blog getBlog(Long idx) {
        return blogJpaRepository
                .findById(idx)
                .map(Blog::toBlog)
                .orElseThrow(()-> BlogNotFoundException.EXCEPTION);
    }

    @Override
    @Transactional
    public void editBlog(EditBlogRequest request) {
        BlogEntity entity = getBlogEntity(request.idx());
        entity.editBlog(request);
    }

    @Override
    @Transactional
    public void addViews(Long idx) {
        BlogEntity entity = getBlogEntity(idx);
        entity.setViews(entity.getViews() + 1);
    }

    @Override
    @Transactional
    public void deleteBlog(Long idx) {
        Blog blog = getBlog(idx);
        blogJpaRepository.deleteById(blog.idx());
    }

    public BlogEntity getBlogEntity(Long idx) {
        return blogJpaRepository
                .findByIdxForUpdate(idx)
                .orElseThrow(()-> BlogNotFoundException.EXCEPTION);
    }

}
