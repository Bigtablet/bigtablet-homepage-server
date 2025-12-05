package com.bigtablet.bigtablethompageserver.domain.talent.application.response;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TalentResponse(
        Long idx,
        String email,
        String name,
        String department,
        String portfolioUrl,
        List<String> etcUrl,
        LocalDateTime createdAt
) {
    public static TalentResponse of(Talent talent) {
        return TalentResponse.builder()
                .idx(talent.idx())
                .email(talent.email())
                .name(talent.name())
                .department(talent.department())
                .portfolioUrl(talent.portfolioUrl())
                .etcUrl(talent.etcUrl())
                .createdAt(talent.createdAt())
                .build();
    }
}
