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
    /**
     * User 도메인 객체를 UserResponse로 변환합니다.
     * @param user User 변환할 도메인 객체
     * @return UserResponse 변환된 응답 DTO
     */
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.email())
                .name(user.name())
                .createdAt(user.createdAt())
                .modifiedAt(user.modifiedAt())
                .build();
    }
}
