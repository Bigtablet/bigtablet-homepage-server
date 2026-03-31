package com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TalentJpaRepository extends JpaRepository<TalentEntity, Long> {

    boolean existsByEmail(String email);

    Optional<TalentEntity> findByIdx(Long idx);

}
