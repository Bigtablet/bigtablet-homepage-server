package com.bigtablet.bigtablethompageserver.domain.blog.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.blog.domain.entity.BlogEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlogJpaRepository extends JpaRepository<BlogEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from BlogEntity b where b.idx = :idx")
    Optional<BlogEntity> findByIdxForUpdate(@Param("idx") Long idx);

}
