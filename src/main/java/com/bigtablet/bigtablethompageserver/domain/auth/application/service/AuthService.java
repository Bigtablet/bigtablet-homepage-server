package com.bigtablet.bigtablethompageserver.domain.auth.application.service;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.user.exception.PasswordWrongException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.EmailCodeValidException;
import com.bigtablet.bigtablethompageserver.global.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;

    /**
     * JWT 토큰 생성 (액세스 + 리프레시)
     * @param email String 사용자 이메일
     * @return JsonWebTokenResponse 액세스 토큰과 리프레시 토큰
     */
    public JsonWebTokenResponse generateToken(String email) {
        log.info("[AuthService] generateToken - email={}", email);
        return JsonWebTokenResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(email))
                .refreshToken(jwtProvider.generateRefreshToken(email))
                .build();
    }

    /**
     * 리프레시 토큰으로 액세스 토큰 재발급
     * @param refreshToken String 리프레시 토큰
     * @return RefreshTokenResponse 재발급된 액세스 토큰
     */
    public RefreshTokenResponse refreshToken(String refreshToken) {
        log.info("[AuthService] refreshToken");
        Jws<Claims> claims = jwtProvider.getClaims(refreshToken);
        return RefreshTokenResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(claims.getBody().getSubject()))
                .build();
    }

    /**
     * 비밀번호 일치 여부 검증
     * @param rawPassword String 평문 비밀번호
     * @param hashedPassword String 해시된 비밀번호
     * @return void
     */
    public void checkPassword(String rawPassword, String hashedPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw PasswordWrongException.EXCEPTION;
        }
    }

    /**
     * 이메일 인증 코드 검증 (Redis 기반)
     * @param email String 사용자 이메일
     * @param authCode String 인증 코드
     * @return void
     */
    public void checkAuthNum(String email, String authCode) {
        log.info("[AuthService] checkAuthNum - email={}", email);
        String code = redisRepository.getByKey(email, String.class);
        if (!Objects.equals(code, authCode)) {
            throw EmailCodeValidException.EXCEPTION;
        }
        redisRepository.delete(email);
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
        redisRepository.save(email, authcode, 180, TimeUnit.SECONDS);
        return authcode;
    }

}
