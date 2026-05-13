package com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JobJpaRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findAllByIsActiveTrueOrderByCreatedAtDesc();

    List<JobEntity> findAllByIsActiveTrueAndTitleContainingIgnoreCase(String title);

    List<JobEntity> findAllByDepartmentAndIsActiveTrue(Department department);

    List<JobEntity> findAllByEducationAndIsActiveTrue(Education education);

    List<JobEntity> findAllByRecruitTypeAndIsActiveTrue(RecruitType recruitType);

    List<JobEntity> findAllByIsActiveFalseOrderByCreatedAtDesc();

    /**
     * 지정된 마감일에 도래한 활성 채용 공고를 일괄 비활성화한다 (스케줄러용).
     * entity hydration 없이 단일 UPDATE를 실행하므로 메모리/I/O 비용이 일정.
     * @param endDate LocalDate 비활성화 대상 마감일
     * @return int 영향받은 row 수
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE JobEntity j SET j.isActive = false WHERE j.endDate = :endDate AND j.isActive = true")
    int deactivateAllByEndDate(@Param("endDate") LocalDate endDate);

}
