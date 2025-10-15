package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsJpaRepository extends JpaRepository<NewsEntity, Long> {

    Optional<NewsEntity> findByIdx(Long idx);

}
