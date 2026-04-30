package com.bigtablet.bigtablethompageserver.domain.job.domain.model;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record JobInput(
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
) {}
