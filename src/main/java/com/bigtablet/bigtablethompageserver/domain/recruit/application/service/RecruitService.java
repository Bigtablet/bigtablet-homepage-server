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

    /**
     * 채용 지원서 저장
     * @param jobId Long 채용 공고 ID
     * @param name String 지원자 이름
     * @param phoneNumber String 전화번호
     * @param email String 이메일
     * @param address String 주소
     * @param addressDetail String 상세 주소
     * @param portfolio String 포트폴리오 URL
     * @param coverLetter String 자기소개서
     * @param profileImage String 프로필 이미지 URL
     * @param educationLevel EducationLevel 학력 수준
     * @param schoolName String 학교명
     * @param admissionYear String 입학 연도
     * @param graduationYear String 졸업 연도
     * @param department String 학과
     * @param military Military 병역 사항
     * @param attachment1 String 첨부파일 1 URL
     * @param attachment2 String 첨부파일 2 URL
     * @param attachment3 String 첨부파일 3 URL
     * @return Recruit 도메인 객체
     */
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
