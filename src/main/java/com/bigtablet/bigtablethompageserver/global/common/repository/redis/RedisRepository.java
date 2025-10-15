package com.bigtablet.bigtablethompageserver.global.common.repository.redis;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {

    <T> void save(String key, T value, int timeout, TimeUnit unit);

    <T> T getByKey(String key, Class<T> type);

    void delete(String key);

}
