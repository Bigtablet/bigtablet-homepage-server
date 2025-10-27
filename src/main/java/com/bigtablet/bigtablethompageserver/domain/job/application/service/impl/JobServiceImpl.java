package com.bigtablet.bigtablethompageserver.domain.job.application.service.impl;

import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.Job;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.request.EditJobRequest;
import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
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
public class JobServiceImpl implements JobService {

    private final JobJpaRepository jobJpaRepository;

    @Override
    public void saveJob(JobEntity jobEntity) {
       jobJpaRepository.save(jobEntity);
    }

    @Override
    public Job getJob(Long idx) {
        return jobJpaRepository
                .findById(idx)
                .map(Job::toJob)
                .orElseThrow(()->JobNotFoundException.EXCEPTION);
    }

    @Override
    public List<Job> getAllJob() {
        return jobJpaRepository
                .findAllByIsActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public List<Job> searchJobByTitle(String title) {
        return jobJpaRepository
                .findAllByTitleAndIsActiveTrue(title)
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public List<Job> searchJobByDepartment(Department department) {
        return jobJpaRepository
                .findAllByDepartmentAndIsActiveTrue(department)
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public List<Job> searchJobByEducation(Education education) {
        return jobJpaRepository
                .findAllByEducationAndIsActiveTrue(education)
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public List<Job> searchJobByRecruitType(RecruitType recruitType) {
        return jobJpaRepository
                .findAllByRecruitTypeAndIsActiveTrue(recruitType)
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public List<Job> getAllJobIsFalse() {
        return jobJpaRepository
                .findAllByIsActiveFalseOrderByCreatedAtDesc()
                .stream()
                .map(Job::toJob)
                .toList();
    }

    @Override
    public void deleteJob(Long idx) {
        jobJpaRepository.deleteById(idx);
    }

    @Override
    @Transactional
    public void editJob(EditJobRequest request) {
        JobEntity entity = jobJpaRepository
                .findById(request.idx())
                .orElseThrow(()->JobNotFoundException.EXCEPTION);
        entity.editJob(request);
    }

    @Override
    public void checkJobsIsEmpty(List<Job> jobs) {
        if (jobs.isEmpty()) {
            throw JobNotFoundException.EXCEPTION;
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
            log.info("🔥 {} | Deleted ended jobs : {}", LocalDateTime.now(), jobsEnded.size());
        }
        log.info("✅ {} | End scheduled job", LocalDateTime.now());
    }

}
