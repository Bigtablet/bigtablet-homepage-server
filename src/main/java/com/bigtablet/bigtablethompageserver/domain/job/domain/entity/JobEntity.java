package com.bigtablet.bigtablethompageserver.domain.job.domain.entity;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.JobInput;
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

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_job")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobEntity extends BaseEntity {

    // 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 공고 제목
    @Column(nullable = false)
    private String title;

    // 부서
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    // 근무지
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    // 채용 유형
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecruitType recruitType;

    // 경력 요건
    @Column(nullable = false)
    private String experiment;

    // 학력 요건
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Education education;

    // 회사 소개
    @Column(nullable = false, columnDefinition = "TEXT")
    private String companyIntroduction;

    // 포지션 소개
    @Column(nullable = false, columnDefinition = "TEXT")
    private String positionIntroduction;

    // 주요 업무
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mainResponsibility;

    // 자격 요건
    @Column(nullable = false, columnDefinition = "TEXT")
    private String qualification;

    // 우대 사항
    @Column(nullable = false, columnDefinition = "TEXT")
    private String preferredQualification;

    // 공고 시작일
    @Column(nullable = false)
    private LocalDate startDate;

    // 공고 마감일
    private LocalDate endDate;

    // 활성 상태
    @Column(nullable = false)
    private boolean isActive;

    /**
     * JobInput으로 새 활성 상태 채용 공고 엔티티를 생성한다
     * @param input 채용 공고 입력 데이터
     * @return JobEntity 신규 채용 공고 엔티티
     */
    public static JobEntity create(JobInput input) {
        return JobEntity.builder()
                .title(input.title())
                .department(input.department())
                .location(input.location())
                .recruitType(input.recruitType())
                .experiment(input.experiment())
                .education(input.education())
                .companyIntroduction(input.companyIntroduction())
                .positionIntroduction(input.positionIntroduction())
                .mainResponsibility(input.mainResponsibility())
                .qualification(input.qualification())
                .preferredQualification(input.preferredQualification())
                .startDate(input.startDate())
                .endDate(input.endDate())
                .isActive(true)
                .build();
    }

    /**
     * 채용 공고 정보를 수정한다
     * @param input 채용 공고 입력 데이터
     */
    public void editJob(JobInput input) {
        this.title = input.title();
        this.department = input.department();
        this.location = input.location();
        this.recruitType = input.recruitType();
        this.experiment = input.experiment();
        this.education = input.education();
        this.companyIntroduction = input.companyIntroduction();
        this.positionIntroduction = input.positionIntroduction();
        this.mainResponsibility = input.mainResponsibility();
        this.qualification = input.qualification();
        this.preferredQualification = input.preferredQualification();
        this.startDate = input.startDate();
        this.endDate = input.endDate();
    }

    /**
     * 채용 공고를 비활성화한다
     */
    public void deactivate() {
        this.isActive = false;
    }

}
