package com.bigtablet.bigtablethompageserver.domain.auth.application.service;

import com.bigtablet.bigtablethompageserver.domain.auth.domain.model.AccessToken;
import com.bigtablet.bigtablethompageserver.domain.auth.domain.model.AuthToken;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // 이메일 인증 코드 Redis TTL (초 단위)
    private static final int AUTH_CODE_TTL_SECONDS = 180;

    private final JwtProvider jwtProvider;
    private final RedisRepository redisRepository;

    /**
     * JWT 토큰 생성 (액세스 + 리프레시)
     * @param email String 사용자 이메일
     * @return AuthToken 액세스 토큰과 리프레시 토큰을 담은 도메인 객체
     */
    public AuthToken generateToken(String email) {
        log.info("[AuthService] generateToken - email={}", email);
        return new AuthToken(
                jwtProvider.generateAccessToken(email),
                jwtProvider.generateRefreshToken(email)
        );
    }

    /**
     * 리프레시 토큰으로 액세스 토큰 재발급
     * @param refreshToken String 리프레시 토큰
     * @return AccessToken 재발급된 액세스 토큰 도메인 객체
     */
    public AccessToken refreshToken(String refreshToken) {
        log.info("[AuthService] refreshToken");
        Jws<Claims> claims = jwtProvider.getClaims(refreshToken);
        return new AccessToken(jwtProvider.generateAccessToken(claims.getBody().getSubject()));
    }

    /**
     * 6자리 랜덤 인증 코드 생성 및 Redis 저장
     * @param email String 사용자 이메일
     * @return String 인증 코드
     */
    public String createRandomNum(String email) {
        log.info("[AuthService] createRandomNum - email={}", email);
        Optional.ofNullable(redisRepository.getByKey(email, String.class))
                .ifPresent(value -> redisRepository.delete(email));
        SecureRandom r = new SecureRandom();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomNumber.append(r.nextInt(10));
        }
        String authcode = randomNumber.toString();
        redisRepository.save(email, authcode, AUTH_CODE_TTL_SECONDS, TimeUnit.SECONDS);
        return authcode;
    }

}
