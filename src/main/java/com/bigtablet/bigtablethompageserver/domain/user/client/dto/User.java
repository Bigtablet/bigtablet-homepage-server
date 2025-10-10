package com.bigtablet.bigtablethompageserver.domain.user.client.dto;

import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;
import com.bigtablet.bigtablethompageserver.domain.user.domain.enums.UserRole;

import java.time.LocalDateTime;

public record User(
        String email,
        String password,
        String name,
        UserRole userRole,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static User toUser(UserEntity entity) {
        return new User(
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getUserRole(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
