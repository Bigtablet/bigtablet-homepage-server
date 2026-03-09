package com.bigtablet.bigtablethompageserver.domain.job.application.service;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobJpaRepository jobJpaRepository;

    @Transactional
    public void save(
            String title,
            Department department,
            Location location,
            RecruitType recruitType,
            String experiment,
            Education education,
            String companyIntroduction,
            String positionIntroduction,
            String mainResponsibility,
            String qualification,
            String preferredQualification,
            LocalDate startDate,
            LocalDate endDate
    ) {
        log.info("[JobService] save - title={}", title);
        jobJpaRepository.save(JobEntity.builder()
                .title(title)
                .department(department)
                .location(location)
                .recruitType(recruitType)
                .experiment(experiment)
                .education(education)
                .companyIntroduction(companyIntroduction)
                .positionIntroduction(positionIntroduction)
                .mainResponsibility(mainResponsibility)
                .qualification(qualification)
                .preferredQualification(preferredQualification)
                .startDate(startDate)
                .endDate(endDate)
                .isActive(true)
                .build());
    }

    @Transactional
    public void edit(
            Long idx,
            String title,
            Department department,
            Location location,
            RecruitType recruitType,
            String experiment,
            Education education,
            String companyIntroduction,
            String positionIntroduction,
            String mainResponsibility,
            String qualification,
            String preferredQualification,
            LocalDate startDate,
            LocalDate endDate
    ) {
        log.info("[JobService] edit - idx={}", idx);
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        entity.editJob(
                title,
                department,
                location,
                recruitType,
                experiment,
                education,
                companyIntroduction,
                positionIntroduction,
                mainResponsibility,
                qualification,
                preferredQualification,
                startDate,
                endDate
        );
    }

    @Transactional
    public void deactivate(Long idx) {
        log.info("[JobService] deactivate - idx={}", idx);
        jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION)
                .deactivate();
    }

    @Transactional
    public void delete(Long idx) {
        log.info("[JobService] delete - idx={}", idx);
        jobJpaRepository.deleteById(idx);
    }

}
