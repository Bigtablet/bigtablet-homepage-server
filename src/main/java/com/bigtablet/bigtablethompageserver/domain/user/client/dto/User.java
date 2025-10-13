package com.bigtablet.bigtablethompageserver.domain.user.client.dto;

import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;

import java.time.LocalDateTime;

public record User(
        String email,
        String password,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static User toUser(UserEntity entity) {
        return new User(
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
