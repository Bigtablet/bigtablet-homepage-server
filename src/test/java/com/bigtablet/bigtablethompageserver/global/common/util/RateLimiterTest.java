package com.bigtablet.bigtablethompageserver.global.common.util;

import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.exception.TooManyRequestsException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RateLimiterTest {

    // 인메모리 카운터로 동작하는 RedisRepository 스텁 (increment만 의미 있음)
    private static final class FakeRedis implements RedisRepository {

        private final Map<String, Long> counters = new HashMap<>();

        @Override
        public <T> void save(String key, T value, int timeout, TimeUnit unit) {}

        @Override
        public <T> T getByKey(String key, Class<T> type) {
            return null;
        }

        @Override
        public void delete(String key) {
            counters.remove(key);
        }

        @Override
        public long increment(String key, Duration ttl) {
            return counters.merge(key, 1L, Long::sum);
        }

    }

    @Test
    void allowsUpToMaxThenThrows() {
        RateLimiter limiter = new RateLimiter(new FakeRedis());
        assertDoesNotThrow(() -> limiter.check("k", 3, Duration.ofMinutes(1)));
        assertDoesNotThrow(() -> limiter.check("k", 3, Duration.ofMinutes(1)));
        assertDoesNotThrow(() -> limiter.check("k", 3, Duration.ofMinutes(1)));
        assertThrows(TooManyRequestsException.class, () -> limiter.check("k", 3, Duration.ofMinutes(1)));
    }

    @Test
    void separateKeysAreIndependent() {
        RateLimiter limiter = new RateLimiter(new FakeRedis());
        assertDoesNotThrow(() -> limiter.check("a", 1, Duration.ofMinutes(1)));
        assertDoesNotThrow(() -> limiter.check("b", 1, Duration.ofMinutes(1)));
        assertThrows(TooManyRequestsException.class, () -> limiter.check("a", 1, Duration.ofMinutes(1)));
    }

}
