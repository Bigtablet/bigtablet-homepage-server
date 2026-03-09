package com.bigtablet.bigtablethompageserver.domain.recruit.application.service;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa.RecruitJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitJpaRepository recruitJpaRepository;

    @Transactional
    public Recruit save(
            Long jobId,
            String name,
            String phoneNumber,
            String email,
            String address,
            String addressDetail,
            String portfolio,
            String coverLetter,
            String profileImage,
            EducationLevel educationLevel,
            String schoolName,
            String admissionYear,
            String graduationYear,
            String department,
            Military military,
            String attachment1,
            String attachment2,
            String attachment3
    ) {
        log.info("[RecruitService] save - jobId={}, name={}", jobId, name);
        RecruitEntity entity = recruitJpaRepository.save(RecruitEntity.builder()
                .jobId(jobId)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .addressDetail(addressDetail)
                .portfolio(portfolio)
                .coverLetter(coverLetter)
                .profileImage(profileImage)
                .educationLevel(educationLevel)
                .schoolName(schoolName)
                .admissionYear(admissionYear)
                .graduationYear(graduationYear)
                .department(department)
                .military(military)
                .attachment1(attachment1)
                .attachment2(attachment2)
                .attachment3(attachment3)
                .status(Status.DOCUMENT)
                .build());
        return Recruit.of(entity);
    }

    @Transactional
    public void editStatus(Status status, Long idx) {
        log.info("[RecruitService] editStatus - idx={}, status={}", idx, status);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.editStatus(status);
    }

    @Transactional
    public void accept(Long idx) {
        log.info("[RecruitService] accept - idx={}", idx);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.accept();
    }

    @Transactional
    public void reject(Long idx) {
        log.info("[RecruitService] reject - idx={}", idx);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.reject();
    }

}
