package com.bigtablet.bigtablethompageserver.domain.recruit.client.request;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record RegisterRecruitRequest(
        @NotNull
        Long jobId,
        @NotBlank
        String name,
        @NotBlank
        String phoneNumber,
        @NotBlank
        @Email(message = "이메일 형식이 틀립니다.")
        String email,
        @NotBlank
        String address,
        @NotBlank
        String addressDetail,
        @NotBlank
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String portfolio,
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String coverLetter,
        @NotBlank
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String profileImage,
        @NotNull
        EducationLevel educationLevel,
        String schoolName,
        @Pattern(
                regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$",
                message = "입학년도는 YYYY-MM 형식이어야 합니다."
        )
        String admissionYear,
        @Pattern(
                regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$",
                message = "졸업년도는 YYYY-MM 형식이어야 합니다."
        )
        String graduationYear,
        String department,
        @NotNull
        Military military,
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String attachment1,
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String attachment2,
        @URL(message = "유효한 URL 형식이어야 합니다.")
        String attachment3
) {}
