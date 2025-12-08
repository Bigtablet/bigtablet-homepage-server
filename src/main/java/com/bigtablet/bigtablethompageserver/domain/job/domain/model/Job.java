package com.bigtablet.bigtablethompageserver.domain.job.domain.model;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record Job(
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
        LocalDate endDate,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Job of(JobEntity entity) {
        return Job.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .department(entity.getDepartment())
                .location(entity.getLocation())
                .recruitType(entity.getRecruitType())
                .experiment(entity.getExperiment())
                .education(entity.getEducation())
                .companyIntroduction(entity.getCompanyIntroduction())
                .positionIntroduction(entity.getPositionIntroduction())
                .mainResponsibility(entity.getMainResponsibility())
                .qualification(entity.getQualification())
                .preferredQualification(entity.getPreferredQualification())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
