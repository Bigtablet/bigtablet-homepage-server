package com.bigtablet.bigtablethompageserver.domain.recruit.application.service;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa.RecruitJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitNotFoundException;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitStatusErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitJpaRepository recruitJpaRepository;

    public Recruit saveRecruit(
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

    public Recruit findById(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        return Recruit.of(entity);
    }

    public List<Recruit> findAll() {
        return recruitJpaRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByJobId(Long jobId) {
        return recruitJpaRepository
                .findAllByJobIdOrderByCreatedAtDesc(jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByStatus(Status status) {
        return recruitJpaRepository
                .findAllByStatusOrderByCreatedAtAsc(status)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByStatusAndJobId(Status status, Long jobId) {
        return recruitJpaRepository
                .findAllByStatusAndJobIdOrderByCreatedAtAsc(status, jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    @Transactional
    public void updateStatus(Status status, Long idx) {
        RecruitEntity entity = getRecruitEntity(idx);
        entity.setStatus(status);
        recruitJpaRepository.save(entity);
    }

    @Transactional
    public void acceptRecruit(Long idx) {
        RecruitEntity entity = getRecruitEntity(idx);
        entity.setStatus(Status.ACCEPTED);
        recruitJpaRepository.save(entity);
    }

    @Transactional
    public void rejectRecruit(Long idx) {
        RecruitEntity entity = getRecruitEntity(idx);
        entity.setStatus(Status.REJECTED);
        recruitJpaRepository.save(entity);
    }

    public void checkRecruitStatus(Long idx) {
        RecruitEntity entity = getRecruitEntity(idx);
        if (entity.getStatus().equals(Status.DOCUMENT)) {
            throw RecruitStatusErrorException.EXCEPTION;
        }
    }

    private RecruitEntity getRecruitEntity(Long idx) {
        return recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
    }

}
