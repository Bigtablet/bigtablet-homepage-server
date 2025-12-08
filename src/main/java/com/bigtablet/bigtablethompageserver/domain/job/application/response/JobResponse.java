package com.bigtablet.bigtablethompageserver.domain.job.application.response;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record JobResponse(
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
    public static JobResponse of(Job job) {
        return JobResponse.builder()
                .idx(job.idx())
                .title(job.title())
                .department(job.department())
                .location(job.location())
                .recruitType(job.recruitType())
                .experiment(job.experiment())
                .education(job.education())
                .companyIntroduction(job.companyIntroduction())
                .positionIntroduction(job.positionIntroduction())
                .mainResponsibility(job.mainResponsibility())
                .qualification(job.qualification())
                .preferredQualification(job.preferredQualification())
                .startDate(job.startDate())
                .endDate(job.endDate())
                .isActive(job.isActive())
                .createdAt(job.createdAt())
                .modifiedAt(job.modifiedAt())
                .build();
    }
}
