package com.bigtablet.bigtablethompageserver.domain.admin.application.service;

import com.bigtablet.bigtablethompageserver.domain.admin.domain.enums.AdminRole;
import com.bigtablet.bigtablethompageserver.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProvider jwtProvider;

    /**
     * Access Token 발급
     * @param adminId String 어드민 ID
     * @param role AdminRole 어드민 권한
     * @return String Access Token
     */
    public String generateAccessToken(String adminId, AdminRole role) {
        return jwtProvider.generateAccessToken(adminId, role.name());
    }

    /**
     * Refresh Token 발급
     * @param adminId String 어드민 ID
     * @return String Refresh Token
     */
    public String generateRefreshToken(String adminId) {
        return jwtProvider.generateRefreshToken(adminId);
    }

}
