package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobJpaRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findAllByIsActiveTrueOrderByCreatedAtDesc();

    List<JobEntity> findAllByTitleAndIsActiveTrue(String title);

    List<JobEntity> findAllByDepartmentAndIsActiveTrue(Department department);

    List<JobEntity> findAllByEducationAndIsActiveTrue(Education education);

    List<JobEntity> findAllByRecruitTypeAndIsActiveTrue(RecruitType recruitType);

    List<JobEntity> findAllByIsActiveFalseOrderByCreatedAtDesc();

}
