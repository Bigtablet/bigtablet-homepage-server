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
    /**
     * TalentEntity를 Talent 도메인 객체로 변환합니다.
     * @param entity TalentEntity 변환할 엔티티
     * @return Talent 변환된 도메인 객체
     */
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

