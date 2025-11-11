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
