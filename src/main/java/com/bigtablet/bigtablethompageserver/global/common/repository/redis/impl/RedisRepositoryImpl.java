package com.bigtablet.bigtablethompageserver.global.common.repository.redis.impl;

import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.config.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
