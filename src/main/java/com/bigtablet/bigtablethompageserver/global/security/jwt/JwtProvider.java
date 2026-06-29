package com.bigtablet.bigtablethompageserver.global.security.jwt;

import com.bigtablet.bigtablethompageserver.global.security.jwt.config.JwtProperties;
import com.bigtablet.bigtablethompageserver.global.security.jwt.enums.JwtType;
import com.bigtablet.bigtablethompageserver.global.security.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    /**
     * JWT 서명 키 생성
     * @return SecretKey HMAC-SHA 서명 키
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWT 토큰 파싱 및 검증. 만료/서명 실패/손상 등 모든 검증 실패는
     * 인증 실패 사유를 노출하지 않도록 InvalidTokenException(401)으로 통일해 던진다.
     * @param token JWT 토큰 문자열
     * @return Jws<Claims> 검증된 클레임 정보
     */
    public Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    /**
     * 액세스 토큰 생성
     * @param subject 토큰 subject (어드민 ID)
     * @param role 권한 클레임 (예: ADMIN)
     * @return String JWT 토큰
     */
    public String generateAccessToken(final String subject, final String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(subject)
                .claim("role", role)
                .claim("token_type", JwtType.ACCESS.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtProperties.getExpiration(), ChronoUnit.MILLIS)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     * @param subject 토큰 subject (어드민 ID)
     * @return String JWT 토큰
     */
    public String generateRefreshToken(final String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(subject)
                .claim("token_type", JwtType.REFRESH.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtProperties.getRefreshExpiration(), ChronoUnit.MILLIS)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

}
