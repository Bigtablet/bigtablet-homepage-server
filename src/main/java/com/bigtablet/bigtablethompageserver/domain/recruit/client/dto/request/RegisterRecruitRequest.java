package com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.EducationLevel;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Military;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.RecruitInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record RegisterRecruitRequest(
        @NotNull
        Long jobId,
        @NotBlank
        @Pattern(regexp = "^[^\\r\\n]+$", message = "이름에 줄바꿈 문자를 포함할 수 없습니다.")
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
) {
    /**
     * Request DTO를 RecruitInput 도메인 입력 데이터로 변환한다 (jobId 오버라이드 지원)
     * @param resolvedJobId 검증/조회된 채용 공고 ID
     * @return RecruitInput 채용 지원서 입력 데이터
     */
    public RecruitInput toRecruitInput(Long resolvedJobId) {
        return RecruitInput.builder()
                .jobId(resolvedJobId)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .addressDetail(addressDetail)
                .portfolio(portfolio)
                .coverLetter(coverLetter)
                .profileImage(profileImage)
                .educationLevel(educationLevel)
                .schoolName(schoolName)
                .admissionYear(admissionYear)
                .graduationYear(graduationYear)
                .department(department)
                .military(military)
                .attachment1(attachment1)
                .attachment2(attachment2)
                .attachment3(attachment3)
                .build();
    }
}

