package com.bigtablet.bigtablethompageserver.domain.admin.domain.model;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.entity.AdminEntity;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.enums.AdminRole;

import lombok.Builder;

@Builder
public record Admin(
        String id,
        String email,
        AdminRole role
) {

    /**
     * AdminEntity를 Admin 도메인 객체로 변환한다.
     * @param entity 변환할 어드민 엔티티
     * @return Admin 변환된 어드민 도메인 객체
     */
    public static Admin of(AdminEntity entity) {
        return Admin.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }

}
