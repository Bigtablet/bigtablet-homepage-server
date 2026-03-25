package com.bigtablet.bigtablethompageserver.domain.job.domain.entity;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
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
     * 채용 공고 정보를 수정한다
     * @param title String 공고 제목
     * @param department Department 부서
     * @param location Location 근무지
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
     */
    public void editJob(
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
        this.title = title;
        this.department = department;
        this.location = location;
        this.recruitType = recruitType;
        this.experiment = experiment;
        this.education = education;
        this.companyIntroduction = companyIntroduction;
        this.positionIntroduction = positionIntroduction;
        this.mainResponsibility = mainResponsibility;
        this.qualification = qualification;
        this.preferredQualification = preferredQualification;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 채용 공고를 비활성화한다
     */
    public void deactivate() {
        this.isActive = false;
    }

}
