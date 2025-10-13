package com.bigtablet.bigtablethompageserver.domain.user.application.response;

import com.bigtablet.bigtablethompageserver.domain.user.client.dto.User;

import java.time.LocalDateTime;

public record UserResponse(
        String email,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.email(),
                user.name(),
                user.createdAt(),
                user.modifiedAt()
        );
    }
}
