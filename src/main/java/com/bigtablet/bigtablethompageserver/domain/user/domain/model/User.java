package com.bigtablet.bigtablethompageserver.domain.user.domain.model;

import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        String email,
        String password,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    /**
     * UserEntity를 User 도메인 객체로 변환합니다.
     * @param entity UserEntity 변환할 엔티티
     * @return User 변환된 도메인 객체
     */
    public static User of(UserEntity entity) {
        return User.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
