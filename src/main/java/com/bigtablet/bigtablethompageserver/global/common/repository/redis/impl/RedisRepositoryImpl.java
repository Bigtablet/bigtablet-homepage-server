package com.bigtablet.bigtablethompageserver.global.common.repository.redis.impl;

import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.config.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisConfig redisConfig;

    /**
     * Redis에 값 저장 (TTL 포함)
     * @param key String 저장 키
     * @param value T 저장할 값
     * @param timeout int 만료 시간
     * @param unit TimeUnit 만료 시간 단위
     */
    @Override
    public <T> void save(String key, T value, int timeout, TimeUnit unit) {
        redisConfig.redisTemplate().opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Redis에서 키로 값 조회
     * @param key String 조회 키
     * @param type Class<T> 반환 타입 클래스
     * @return T 조회된 값
     */
    @Override
    public <T> T getByKey(String key, Class<T> type) {
        return type.cast(redisConfig.redisTemplate().opsForValue().get(key));
    }

    /**
     * Redis에서 키 삭제
     * @param key String 삭제할 키
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
        if (count != null && count == 1L) {
            // ponytail: INCR 후 별도 EXPIRE라 그 사이 프로세스 종료 시 키가 영구화될 수 있다. 레이트리밋엔 무해(보수적). Lua로 원자화는 처리량 필요 시.
            redisConfig.redisTemplate().expire(key, ttl);
        }
        return count == null ? 0L : count;
    }

}
