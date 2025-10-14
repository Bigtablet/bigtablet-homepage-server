package com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRecruitRequest(
        @NotNull
        Long jobId,
        @NotBlank
        String name,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String email,
        @NotBlank
        String address,
        @NotBlank
        String addressDetail,
        @NotBlank
        String portfolio,
        String coverLetter,
        @NotNull
        EducationLevel educationLevel,
        String schoolName,
        String admissionYear,
        String graduationYear,
        String department,
        @NotNull
        Military military,
        String attachment1,
        String attachment2,
        String attachment3
) {}
