package com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentJpaRepository extends JpaRepository<TalentEntity, Long> {
}
