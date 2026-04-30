package com.bigtablet.bigtablethompageserver.domain.auth.application.response;

import com.bigtablet.bigtablethompageserver.domain.auth.domain.model.AuthToken;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record JsonWebTokenResponse (

    String accessToken,
    String refreshToken

){
    public static JsonWebTokenResponse of(AuthToken authToken) {
        return new JsonWebTokenResponse(
                authToken.accessToken(),
                authToken.refreshToken()
        );
    }
}
