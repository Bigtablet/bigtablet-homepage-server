package com.bigtablet.bigtablethompageserver.global.common.repository.redis.impl;

import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.config.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisConfig redisConfig;

    /**
     * Redis에 값 저장 (TTL 포함)
     * @param key 저장 키
     * @param value 저장할 값
     * @param timeout 만료 시간
     * @param unit 만료 시간 단위
     */
    @Override
    public <T> void save(String key, T value, int timeout, TimeUnit unit) {
        redisConfig.redisTemplate().opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Redis에서 키로 값 조회
     * @param key 조회 키
     * @param type 반환 타입 클래스
     * @return T 조회된 값
     */
    @Override
    public <T> T getByKey(String key, Class<T> type) {
        return type.cast(redisConfig.redisTemplate().opsForValue().get(key));
    }

    /**
     * Redis에서 키 삭제
     * @param key 삭제할 키
     */
    @Override
    public void delete(String key) {
        redisConfig.redisTemplate().delete(key);
    }

    /**
     * 키 값을 원자적으로 1 증가시킨다. 최초 증가(값이 1) 시 TTL을 설정한다. (고정 윈도우 카운터)
     * @param key 카운터 키
     * @param ttl 최초 생성 시 적용할 만료 시간
     * @return 증가 후 카운트
     */
    @Override
    public long increment(String key, Duration ttl) {
        Long count = redisConfig.redisTemplate().opsForValue().increment(key);
        if (count == null) {
            // 드문 경우: INCR 결과가 null. 실제 Redis 연결 장애는 예외로 전파되어 해당 요청이 실패(fail-closed)한다. 모니터링용 경고만 남긴다.
            log.warn("[RedisRepositoryImpl] increment returned null for key={}", key);
            return 0L;
        }
        if (count == 1L) {
            redisConfig.redisTemplate().expire(key, ttl);
        } else {
            // 자가 치유: INCR 직후 EXPIRE 전 장애로 TTL이 유실(-1)된 키에 만료를 재설정해 영구 잠금을 방지
            Long expire = redisConfig.redisTemplate().getExpire(key);
            if (expire != null && expire == -1L) {
                redisConfig.redisTemplate().expire(key, ttl);
            }
        }
        return count;
    }

}
