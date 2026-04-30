package com.bigtablet.bigtablethompageserver.domain.recruit.application.service;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.RecruitInput;
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

    /**
     * 채용 지원서 저장
     * @param input RecruitInput 채용 지원서 입력 데이터
     * @return Recruit 도메인 객체
     */
    @Transactional
    public Recruit save(RecruitInput input) {
        log.info("[RecruitService] save - jobId={}, name={}", input.jobId(), input.name());
        RecruitEntity entity = recruitJpaRepository.save(RecruitEntity.builder()
                .jobId(input.jobId())
                .name(input.name())
                .phoneNumber(input.phoneNumber())
                .email(input.email())
                .address(input.address())
                .addressDetail(input.addressDetail())
                .portfolio(input.portfolio())
                .coverLetter(input.coverLetter())
                .profileImage(input.profileImage())
                .educationLevel(input.educationLevel())
                .schoolName(input.schoolName())
                .admissionYear(input.admissionYear())
                .graduationYear(input.graduationYear())
                .department(input.department())
                .military(input.military())
                .attachment1(input.attachment1())
                .attachment2(input.attachment2())
                .attachment3(input.attachment3())
                .status(Status.DOCUMENT)
                .build());
        return Recruit.of(entity);
    }

    /**
     * 지원서 상태 변경
     * @param status Status 변경할 상태
     * @param idx Long 지원서 ID
     * @return void
     */
    @Transactional
    public void editStatus(Status status, Long idx) {
        log.info("[RecruitService] editStatus - idx={}, status={}", idx, status);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.editStatus(status);
    }

    /**
     * 지원자 최종 합격 처리
     * @param idx Long 지원서 ID
     * @return void
     */
    @Transactional
    public void accept(Long idx) {
        log.info("[RecruitService] accept - idx={}", idx);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.accept();
    }

    /**
     * 지원자 최종 불합격 처리
     * @param idx Long 지원서 ID
     * @return void
     */
    @Transactional
    public void reject(Long idx) {
        log.info("[RecruitService] reject - idx={}", idx);
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        entity.reject();
    }

}
