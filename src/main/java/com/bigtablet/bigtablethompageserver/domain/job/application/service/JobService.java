package com.bigtablet.bigtablethompageserver.domain.job.application.service;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobIsExpiredException;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobJpaRepository jobJpaRepository;

    public void saveJob(
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

    public Job findById(Long idx) {
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        return Job.of(entity);
    }

    public void deleteById(Long idx) {
        jobJpaRepository.deleteById(idx);
    }

    @Transactional
    public void updateJob(
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
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        entity.update(
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

    public void deactivateJob(Long idx) {
        jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION)
                .deactivate();
    }

    public void checkJobIsExpired(Job job) {
        if (!job.isActive()) {
            throw JobIsExpiredException.EXCEPTION;
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void deactivateEndedJobs() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<JobEntity> jobsEnded = jobJpaRepository.findAll().stream()
                .filter(j -> j.getEndDate() != null
                        && j.getEndDate().plusDays(1).isEqual(today))
                .toList();
        if (!jobsEnded.isEmpty()) {
            jobsEnded.forEach(j -> j.setActive(false));
            jobJpaRepository.saveAll(jobsEnded);
            log.info("🔥 {} | Deactivated ended jobs : {}", LocalDateTime.now(), jobsEnded.size());
        }
        log.info("✅ {} | End scheduled job", LocalDateTime.now());
    }

}
