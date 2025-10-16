package com.bigtablet.bigtablethompageserver.domain.auth.application.service.impl;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.service.AuthService;
import com.bigtablet.bigtablethompageserver.domain.user.exception.PasswordWrongException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.EmailCodeValidException;
import com.bigtablet.bigtablethompageserver.global.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;

    @Override
    public JsonWebTokenResponse generateToken(String email) {
        return JsonWebTokenResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(email))
                .refreshToken(jwtProvider.generateRefreshToken(email))
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(String refreshToken) {
        Jws<Claims> claims = jwtProvider.getClaims(refreshToken);
        return RefreshTokenResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(claims.getBody().getSubject()))
                .build();
    }

    @Override
    public void checkPassword(String rawPassword, String hashedPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw PasswordWrongException.EXCEPTION;
        }
    }

    @Override
    public void checkAuthNum(String email, String authCode) {
        String code = redisRepository.getByKey(email, String.class);
        if (!Objects.equals(code, authCode)) {
            throw EmailCodeValidException.EXCEPTION;
        }
        redisRepository.delete(email);
    }

    @Override
    public String createRandomNum(String email) {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomNumber.append(r.nextInt(10));
        }
        String authcode = randomNumber.toString();
        redisRepository.save(email, authcode, 180, TimeUnit.SECONDS);
        return authcode;
    }

}