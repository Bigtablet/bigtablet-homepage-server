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

    /**
     * 블로그 저장
     * @param titleKr 한국어 제목
     * @param titleEn 영어 제목
     * @param contentKr 한국어 내용
     * @param contentEn 영어 내용
     * @param imageUrl 이미지 URL
     */
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

    /**
     * 블로그 수정
     * @param idx 블로그 ID
     * @param titleKr 한국어 제목
     * @param titleEn 영어 제목
     * @param contentKr 한국어 내용
     * @param contentEn 영어 내용
     * @param imageUrl 이미지 URL
     */
    @Transactional
    public void edit(Long idx, String titleKr, String titleEn, String contentKr, String contentEn, String imageUrl) {
        log.info("[BlogService] edit - idx={}", idx);
        BlogEntity entity = getBlogEntity(idx);
        entity.editBlog(titleKr, titleEn, contentKr, contentEn, imageUrl);
    }

    /**
     * 블로그 조회수 증가
     * @param idx 블로그 ID
     */
    @Transactional
    public void addViews(Long idx) {
        log.info("[BlogService] addViews - idx={}", idx);
        BlogEntity entity = getBlogEntity(idx);
        entity.addViews();
    }

    /**
     * 블로그 삭제
     * @param idx 블로그 ID
     */
    @Transactional
    public void delete(Long idx) {
        log.info("[BlogService] delete - idx={}", idx);
        BlogEntity entity = getBlogEntity(idx);
        blogJpaRepository.delete(entity);
    }

    /**
     * ID로 BlogEntity 조회 (비관적 락)
     * @param idx 블로그 ID
     * @return BlogEntity 블로그 엔티티
     */
    private BlogEntity getBlogEntity(Long idx) {
        return blogJpaRepository
                .findByIdxForUpdate(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
    }

}
