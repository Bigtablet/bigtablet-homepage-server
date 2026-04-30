package com.bigtablet.bigtablethompageserver.domain.auth.domain.model;

import lombok.Builder;

@Builder
public record AccessToken(
        String accessToken
) {}
