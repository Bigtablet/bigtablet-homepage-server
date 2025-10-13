package com.bigtablet.bigtablethompageserver.domain.recruit.api.dto;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;

import java.time.LocalDateTime;

public record Recruit(
        Long idx,
        Long jobId,
        String name,
        String phoneNumber,
        String email,
        String address,
        String addressDetail,
        String portfolio,
        String coverLetter,
        EducationLevel educationLevel,
        String schoolName,
        String admissionYear,
        String graduationYear,
        String department,
        Military military,
        String attachment1,
        String attachment2,
        String attachment3,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Recruit toRecruit(RecruitEntity entity) {
        return new Recruit(
                entity.getIdx(),
                entity.getJobId(),
                entity.getName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getAddressDetail(),
                entity.getPortfolio(),
                entity.getCoverLetter(),
                entity.getEducationLevel(),
                entity.getSchoolName(),
                entity.getAdmissionYear(),
                entity.getGraduationYear(),
                entity.getDepartment(),
                entity.getMilitary(),
                entity.getAttachment1(),
                entity.getAttachment2(),
                entity.getAttachment3(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
