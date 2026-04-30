package com.bigtablet.bigtablethompageserver.domain.auth.domain.model;

import lombok.Builder;

@Builder
public record AuthToken(
        String accessToken,
        String refreshToken
) {}
