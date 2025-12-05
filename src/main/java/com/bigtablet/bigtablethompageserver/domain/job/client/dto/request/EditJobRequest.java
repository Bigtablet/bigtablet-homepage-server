package com.bigtablet.bigtablethompageserver.domain.job.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EditJobRequest(
        @NotNull
        Long idx,
        @NotBlank
        String title,
        @NotNull
        Department department,
        @NotNull
        Location location,
        @NotNull
        RecruitType recruitType,
        @NotBlank
        String experiment,
        @NotNull
        Education education,
        @NotBlank
        String companyIntroduction,
        @NotBlank
        String positionIntroduction,
        @NotBlank
        String mainResponsibility,
        @NotBlank
        String qualification,
        @NotBlank
        String preferredQualification,
        @NotNull
        LocalDate startDate,
        LocalDate endDate
) {}
