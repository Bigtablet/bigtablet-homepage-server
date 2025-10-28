package com.bigtablet.bigtablethompageserver.domain.job.client.dto;
import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Job(
        Long idx,
        String title,
        Department department,
        Location location,
        RecruitType recruitType,
        String experiment,
        Education education,
        String companyIntroduction,
        String mainResponsibility,
        String qualification,
        String preferredQualification,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Job toJob(JobEntity entity) {
        return new Job(
                entity.getIdx(),
                entity.getTitle(),
                entity.getDepartment(),
                entity.getLocation(),
                entity.getRecruitType(),
                entity.getExperiment(),
                entity.getEducation(),
                entity.getCompanyIntroduction(),
                entity.getMainResponsibility(),
                entity.getQualification(),
                entity.getPreferredQualification(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
