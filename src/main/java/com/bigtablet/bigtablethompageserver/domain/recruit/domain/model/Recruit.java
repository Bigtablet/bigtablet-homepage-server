package com.bigtablet.bigtablethompageserver.domain.recruit.domain.model;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
        String profileImage,
        EducationLevel educationLevel,
        String schoolName,
        String admissionYear,
        String graduationYear,
        String department,
        Military military,
        String attachment1,
        String attachment2,
        String attachment3,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Recruit of(RecruitEntity entity) {
        return Recruit.builder()
                .idx(entity.getIdx())
                .jobId(entity.getJobId())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .addressDetail(entity.getAddressDetail())
                .portfolio(entity.getPortfolio())
                .coverLetter(entity.getCoverLetter())
                .profileImage(entity.getProfileImage())
                .educationLevel(entity.getEducationLevel())
                .schoolName(entity.getSchoolName())
                .admissionYear(entity.getAdmissionYear())
                .graduationYear(entity.getGraduationYear())
                .department(entity.getDepartment())
                .military(entity.getMilitary())
                .attachment1(entity.getAttachment1())
                .attachment2(entity.getAttachment2())
                .attachment3(entity.getAttachment3())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
