package com.bigtablet.bigtablethompageserver.domain.auth.application.service;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.user.domain.enums.UserRole;

public interface AuthService {

    JsonWebTokenResponse generateToken(String email, UserRole role);

    RefreshTokenResponse refreshToken(String refreshToken);

    void checkPassword(String rawPassword, String hashedPassword);

}