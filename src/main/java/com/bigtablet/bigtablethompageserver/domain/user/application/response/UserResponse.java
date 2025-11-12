package com.bigtablet.bigtablethompageserver.domain.user.application.response;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String email,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.email())
                .name(user.name())
                .createdAt(user.createdAt())
                .modifiedAt(user.modifiedAt())
                .build();
    }
}
