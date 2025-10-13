package com.bigtablet.bigtablethompageserver.domain.auth.application.service;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;

public interface AuthService {

    JsonWebTokenResponse generateToken(String email);

    RefreshTokenResponse refreshToken(String refreshToken);

    void checkPassword(String rawPassword, String hashedPassword);

}