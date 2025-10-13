package com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitJpaRepository extends JpaRepository<RecruitEntity, Long> {
}
