package com.bigtablet.bigtablethompageserver.domain.recruit.application.response;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RecruitResponse(
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
    public static RecruitResponse of(Recruit recruit) {
        return RecruitResponse.builder()
                .idx(recruit.idx())
                .jobId(recruit.jobId())
                .name(recruit.name())
                .phoneNumber(recruit.phoneNumber())
                .email(recruit.email())
                .address(recruit.address())
                .addressDetail(recruit.addressDetail())
                .portfolio(recruit.portfolio())
                .coverLetter(recruit.coverLetter())
                .profileImage(recruit.profileImage())
                .educationLevel(recruit.educationLevel())
                .schoolName(recruit.schoolName())
                .admissionYear(recruit.admissionYear())
                .graduationYear(recruit.graduationYear())
                .department(recruit.department())
                .military(recruit.military())
                .attachment1(recruit.attachment1())
                .attachment2(recruit.attachment2())
                .attachment3(recruit.attachment3())
                .status(recruit.status())
                .createdAt(recruit.createdAt())
                .modifiedAt(recruit.modifiedAt())
                .build();
    }
}
