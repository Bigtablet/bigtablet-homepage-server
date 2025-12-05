package com.bigtablet.bigtablethompageserver.domain.talent.domain.model;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Talent(
        Long idx,
        String email,
        String name,
        String department,
        String portfolioUrl,
        List<String> etcUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static Talent of(TalentEntity entity) {
        return Talent.builder()
                .idx(entity.getIdx())
                .email(entity.getEmail())
                .name(entity.getName())
                .department(entity.getDepartment())
                .portfolioUrl(entity.getPortfolioUrl())
                .etcUrl(entity.getEtcUrl())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}

