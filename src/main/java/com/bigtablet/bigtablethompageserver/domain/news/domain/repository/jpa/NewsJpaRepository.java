package com.bigtablet.bigtablethompageserver.domain.news.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.news.domain.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsJpaRepository extends JpaRepository<NewsEntity, Long> {

}
