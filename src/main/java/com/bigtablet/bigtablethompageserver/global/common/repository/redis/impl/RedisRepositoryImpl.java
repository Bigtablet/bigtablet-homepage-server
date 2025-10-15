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

    @Override
    public <T> void save(String key, T value, int timeout, TimeUnit unit) {
        redisConfig.redisTemplate().opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public <T> T getByKey(String key, Class<T> type) {
        return type.cast(redisConfig.redisTemplate().opsForValue().get(key));
    }

    @Override
    public void delete(String key) {
        redisConfig.redisTemplate().delete(key);
    }

}
