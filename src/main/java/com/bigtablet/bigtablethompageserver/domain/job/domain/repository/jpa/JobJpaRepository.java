package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobJpaRepository extends JpaRepository<JobEntity, Long> {
}
