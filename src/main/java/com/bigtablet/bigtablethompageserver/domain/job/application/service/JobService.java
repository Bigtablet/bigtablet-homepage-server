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

    /**
     * 채용 공고 저장
     * @param title String 공고 제목
     * @param department Department 부서
     * @param location Location 근무 지역
     * @param recruitType RecruitType 채용 유형
     * @param experiment String 경력 요건
     * @param education Education 학력 요건
     * @param companyIntroduction String 회사 소개
     * @param positionIntroduction String 포지션 소개
     * @param mainResponsibility String 주요 업무
     * @param qualification String 자격 요건
     * @param preferredQualification String 우대 사항
     * @param startDate LocalDate 공고 시작일
     * @param endDate LocalDate 공고 마감일
     * @return void
     */
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

    /**
     * 채용 공고 수정
     * @param idx Long 채용 공고 ID
     * @param title String 공고 제목
     * @param department Department 부서
     * @param location Location 근무 지역
     * @param recruitType RecruitType 채용 유형
     * @param experiment String 경력 요건
     * @param education Education 학력 요건
     * @param companyIntroduction String 회사 소개
     * @param positionIntroduction String 포지션 소개
     * @param mainResponsibility String 주요 업무
     * @param qualification String 자격 요건
     * @param preferredQualification String 우대 사항
     * @param startDate LocalDate 공고 시작일
     * @param endDate LocalDate 공고 마감일
     * @return void
     */
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

    /**
     * 채용 공고 비활성화
     * @param idx Long 채용 공고 ID
     * @return void
     */
    @Transactional
    public void deactivate(Long idx) {
        log.info("[JobService] deactivate - idx={}", idx);
        jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION)
                .deactivate();
    }

    /**
     * 채용 공고 삭제
     * @param idx Long 채용 공고 ID
     * @return void
     */
    @Transactional
    public void delete(Long idx) {
        log.info("[JobService] delete - idx={}", idx);
        jobJpaRepository.deleteById(idx);
    }

}
