package com.bigtablet.bigtablethompageserver.domain.auth.domain.model;

public record AuthToken(
        String accessToken,
        String refreshToken
) {}
