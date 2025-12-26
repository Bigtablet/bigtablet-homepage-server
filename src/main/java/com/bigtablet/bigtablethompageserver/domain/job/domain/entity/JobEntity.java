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
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@Table(name = "tb_job")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecruitType recruitType;

    @Column(nullable = false)
    private String experiment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Education education;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String companyIntroduction;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String positionIntroduction;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mainResponsibility;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String qualification;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String preferredQualification;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Setter
    @Column(nullable = false)
    private boolean isActive;

    public void update(
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

    public void deactivate() {
        this.isActive = false;
    }

}
