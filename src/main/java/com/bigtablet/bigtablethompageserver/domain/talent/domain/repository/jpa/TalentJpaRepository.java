package com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TalentJpaRepository extends JpaRepository<TalentEntity, Long> {

    Optional<TalentEntity> findByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TalentEntity t where t.idx = :idx")
    Optional<TalentEntity> findByIdx(@Param("idx") Long idx);

}
