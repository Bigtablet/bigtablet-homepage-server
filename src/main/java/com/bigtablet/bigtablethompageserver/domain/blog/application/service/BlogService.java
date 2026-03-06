package com.bigtablet.bigtablethompageserver.domain.blog.application.service;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa.BlogJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogJpaRepository blogJpaRepository;

    @Transactional
    public void save(String titleKr, String titleEn, String contentKr, String contentEn, String imageUrl) {
        log.info("[BlogService] save - titleKr={}", titleKr);
        blogJpaRepository.save(BlogEntity.builder()
                .titleKr(titleKr)
                .titleEn(titleEn)
                .contentKr(contentKr)
                .contentEn(contentEn)
                .imageUrl(imageUrl)
                .views(0)
                .build());
    }

    @Transactional
    public void edit(Long idx, String titleKr, String titleEn, String contentKr, String contentEn, String imageUrl) {
        log.info("[BlogService] edit - idx={}", idx);
        BlogEntity entity = getBlogEntity(idx);
        entity.editBlog(titleKr, titleEn, contentKr, contentEn, imageUrl);
    }

    @Transactional
    public void addViews(Long idx) {
        log.info("[BlogService] addViews - idx={}", idx);
        BlogEntity entity = getBlogEntity(idx);
        entity.addViews();
    }

    @Transactional
    public void delete(Long idx) {
        log.info("[BlogService] delete - idx={}", idx);
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
