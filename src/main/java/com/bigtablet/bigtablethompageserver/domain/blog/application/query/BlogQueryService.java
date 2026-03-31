package com.bigtablet.bigtablethompageserver.domain.blog.application.query;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.model.Blog;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa.BlogJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.query.BlogQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.blog.exception.BlogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogQueryService {

    private final BlogJpaRepository blogJpaRepository;
    private final BlogQueryRepository blogQueryRepository;

    /**
     * ID로 블로그 조회
     * @param idx Long 블로그 ID
     * @return Blog 블로그 도메인 객체
     */
    public Blog find(Long idx) {
        BlogEntity entity = blogJpaRepository
                .findById(idx)
                .orElseThrow(() -> BlogNotFoundException.EXCEPTION);
        return Blog.of(entity);
    }

    /**
     * 블로그 목록 페이지 조회
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @return List<Blog> 블로그 도메인 객체 목록
     */
    public List<Blog> findAll(int page, int size) {
        return blogQueryRepository.findAll(page, size);
    }

    /**
     * 제목으로 블로그 검색
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @param title String 검색할 블로그 제목
     * @return List<Blog> 블로그 도메인 객체 목록
     */
    public List<Blog> findAllByTitle(int page, int size, String title) {
        return blogQueryRepository.findAllByTitle(page, size, title);
    }

}
