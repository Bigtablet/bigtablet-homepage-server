package com.bigtablet.bigtablethompageserver.global.common.util;

import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.exception.TooManyRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimiter {

    private final RedisRepository redisRepository;

    /**
     * 고정 윈도우 카운터 기반 레이트리밋 — 윈도우 내 호출 수가 maxRequests를 넘으면 예외를 던진다.
     * @param key 카운터 Redis 키 (호출자가 식별자 prefix 포함)
     * @param maxRequests 윈도우당 허용 호출 수
     * @param window 카운터 만료 윈도우
     */
    public void check(final String key, final int maxRequests, final Duration window) {
        long count = redisRepository.increment(key, window);
        if (count > maxRequests) {
            throw TooManyRequestsException.EXCEPTION;
        }
    }

    /**
     * 클라이언트 IP. 클라이언트가 위조 가능한 X-Forwarded-For를 직접 파싱하지 않고,
     * server.forward-headers-strategy=native로 Tomcat RemoteIpValve가 신뢰 프록시 기준으로 정규화한 remoteAddr를 사용한다.
     * (신뢰 프록시 범위는 server.tomcat.remoteip.* 로 더 좁힐 수 있다.)
     * @param request HTTP 요청
     * @return 클라이언트 IP 문자열
     */
    public static String clientIp(final HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
