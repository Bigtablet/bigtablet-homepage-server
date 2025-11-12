package com.bigtablet.bigtablethompageserver.domain.blog.application.service;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa.BlogJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogJpaRepository blogJpaRepository;

    public void saveBlog(String titleKr, String titleEn, String contentKr, String contentEn, String imageUrl) {
        blogJpaRepository.save(BlogEntity.builder()
                .titleKr(titleKr)
                .titleEn(titleEn)
                .contentKr(contentKr)
                .contentEn(contentEn)
                .imageUrl(imageUrl)
                .views(0)
                .build());
    }

    public Blog findById(Long idx) {
        BlogEntity entity = blogJpaRepository
                .findById(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
        return Blog.of(entity);
    }

    @Transactional
    public void editBlog(Long idx, String titleKr, String titleEn, String contentKr, String contentEn, String imageUrl) {
        BlogEntity entity = getBlogEntity(idx);
        entity.update(titleKr, titleEn, contentKr, contentEn, imageUrl);
    }

    @Transactional
    public void addViews(Long idx) {
        BlogEntity entity = getBlogEntity(idx);
        entity.setViews(entity.getViews() + 1);
    }

    @Transactional
    public void deleteBlog(Long idx) {
        BlogEntity entity = blogJpaRepository
                .findById(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
        blogJpaRepository.deleteById(entity.getIdx());
    }

    private BlogEntity getBlogEntity(Long idx) {
        return blogJpaRepository
                .findByIdxForUpdate(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
    }

}
