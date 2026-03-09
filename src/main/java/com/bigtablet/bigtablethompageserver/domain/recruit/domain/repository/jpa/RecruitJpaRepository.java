package com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitJpaRepository extends JpaRepository<RecruitEntity, Long> {

    List<RecruitEntity> findAllByJobIdOrderByCreatedAtDesc(Long jobId);

    List<RecruitEntity> findAllByOrderByCreatedAtDesc();

    List<RecruitEntity> findAllByStatusOrderByCreatedAtAsc(Status status);

    List<RecruitEntity> findAllByStatusAndJobIdOrderByCreatedAtAsc(Status status, Long jobId);

}
