package com.bigtablet.bigtablethompageserver.global.security.jwt;

import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.domain.user.domain.repository.jpa.UserJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.user.exception.UserNotFoundException;
import com.bigtablet.bigtablethompageserver.global.security.auth.CustomUserDetails;
import com.bigtablet.bigtablethompageserver.global.security.jwt.enums.JwtType;
import com.bigtablet.bigtablethompageserver.global.security.jwt.exception.TokenTypeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtExtract {

    private final UserJpaRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * JWT 토큰에서 인증 정보 추출 및 Authentication 생성
     * @param token String JWT 토큰 문자열
     * @return Authentication 인증 객체
     */
    public Authentication getAuthentication(final String token) {
        final Jws<Claims> jws = jwtProvider.getClaims(token);
        final Claims claims = jws.getPayload();
        if (isWrongType(claims, JwtType.ACCESS)) {
            throw TokenTypeException.EXCEPTION;
        }
        User user = userRepository
                .findByEmail(claims.getSubject())
                .map(User::of)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        final CustomUserDetails details = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    /**
     * HTTP 요청에서 JWT 토큰 추출
     * @param request HttpServletRequest HTTP 요청 객체
     * @return String 토큰
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        return extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    /**
     * Bearer 접두사 제거 후 토큰 반환
     * @param token String Authorization 헤더 값
     * @return String 토큰
     */
    public String extractToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    /**
     * 토큰 타입 불일치 여부 확인
     * @param claims Claims JWT 클레임 정보
     * @param jwtType JwtType 기대하는 토큰 타입
     * @return boolean 불일치 여부
     */
    public boolean isWrongType(final Claims claims, final JwtType jwtType) {
        return !(claims.get("token_type").equals(jwtType.toString()));
    }

}