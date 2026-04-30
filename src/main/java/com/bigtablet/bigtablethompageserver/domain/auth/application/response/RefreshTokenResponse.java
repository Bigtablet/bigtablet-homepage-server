package com.bigtablet.bigtablethompageserver.domain.auth.application.response;

import com.bigtablet.bigtablethompageserver.domain.auth.domain.model.AccessToken;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record RefreshTokenResponse (

    String accessToken

){
    public static RefreshTokenResponse of(AccessToken accessToken) {
        return RefreshTokenResponse.builder()
                .accessToken(accessToken.accessToken())
                .build();
    }
}
