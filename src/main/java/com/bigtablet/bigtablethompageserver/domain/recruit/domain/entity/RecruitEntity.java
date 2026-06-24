package com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.RecruitInput;
import com.bigtablet.bigtablethompageserver.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_recruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitEntity extends BaseEntity {

    // 고유 ID
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 채용 공고 ID
    @Column(nullable = false)
    private Long jobId;

    // 지원자 이름
    @Column(nullable = false)
    private String name;

    // 전화번호
    @Column(nullable = false)
    private String phoneNumber;

    // 이메일
    @Column(nullable = false)
    private String email;

    // 주소
    @Column(nullable = false)
    private String address;

    // 상세 주소
    @Column(nullable = false)
    private String addressDetail;

    // 포트폴리오 URL
    @Column(nullable = false)
    private String portfolio;

    // 자기소개서
    private String coverLetter;

    // 프로필 이미지 URL
    @Column(nullable = false)
    private String profileImage;

    // 학력
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    // 학교명
    private String schoolName;

    // 입학년도
    private String admissionYear;

    // 졸업년도
    private String graduationYear;

    // 학과
    private String department;

    // 병역사항
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Military military;

    // 첨부파일 URL 1
    private String attachment1;

    // 첨부파일 URL 2
    private String attachment2;

    // 첨부파일 URL 3
    private String attachment3;

    // 전형 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * RecruitInput으로 새 지원서 엔티티를 생성한다 (서류 전형 상태)
     * @param input 채용 지원서 입력 데이터
     * @return RecruitEntity 신규 지원서 엔티티
     */
    public static RecruitEntity create(RecruitInput input) {
        return RecruitEntity.builder()
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
                .build();
    }

    /**
     * 지원서 전형 상태를 변경한다
     * @param status 변경할 전형 상태
     */
    public void editStatus(Status status) {
        this.status = status;
    }

    /**
     * 지원자를 최종 합격 처리한다
     */
    public void accept() {
        this.status = Status.ACCEPTED;
    }

    /**
     * 지원자를 최종 불합격 처리한다
     */
    public void reject() {
        this.status = Status.REJECTED;
    }

}
