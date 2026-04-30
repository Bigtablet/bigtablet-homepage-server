package com.bigtablet.bigtablethompageserver.domain.job.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Location;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.JobInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterJobRequest(
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
) {
    /**
     * Request DTO를 JobInput 도메인 입력 데이터로 변환한다
     * @return JobInput 채용 공고 입력 데이터
     */
    public JobInput toJobInput() {
        return JobInput.builder()
                .title(title)
                .department(department)
                .location(location)
                .recruitType(recruitType)
                .experiment(experiment)
                .education(education)
                .companyIntroduction(companyIntroduction)
                .positionIntroduction(positionIntroduction)
                .mainResponsibility(mainResponsibility)
                .qualification(qualification)
                .preferredQualification(preferredQualification)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
