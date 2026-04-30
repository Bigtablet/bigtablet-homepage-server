package com.bigtablet.bigtablethompageserver.domain.recruit.domain.model;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;

import lombok.Builder;

@Builder
public record RecruitInput(
        Long jobId,
        String name,
        String phoneNumber,
        String email,
        String address,
        String addressDetail,
        String portfolio,
        String coverLetter,
        String profileImage,
        EducationLevel educationLevel,
        String schoolName,
        String admissionYear,
        String graduationYear,
        String department,
        Military military,
        String attachment1,
        String attachment2,
        String attachment3
) {}
