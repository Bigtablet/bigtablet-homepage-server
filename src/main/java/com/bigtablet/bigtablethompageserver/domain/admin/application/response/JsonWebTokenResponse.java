package com.bigtablet.bigtablethompageserver.domain.admin.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record JsonWebTokenResponse(
        String accessToken,
        String refreshToken
) {}
