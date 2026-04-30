package com.bigtablet.bigtablethompageserver.domain.auth.application.response;

import com.bigtablet.bigtablethompageserver.domain.auth.domain.model.AuthToken;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record JsonWebTokenResponse (

    String accessToken,
    String refreshToken

){
    public static JsonWebTokenResponse of(AuthToken authToken) {
        return JsonWebTokenResponse.builder()
                .accessToken(authToken.accessToken())
                .refreshToken(authToken.refreshToken())
                .build();
    }
}
