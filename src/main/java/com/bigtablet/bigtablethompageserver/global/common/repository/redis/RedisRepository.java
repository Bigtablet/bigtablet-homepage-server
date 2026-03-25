package com.bigtablet.bigtablethompageserver.global.common.repository.redis;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {

    /**
     * Redis에 값 저장 (TTL 포함)
     * @param key String 저장 키
     * @param value T 저장할 값
     * @param timeout int 만료 시간
     * @param unit TimeUnit 만료 시간 단위
     */
    <T> void save(String key, T value, int timeout, TimeUnit unit);

    /**
     * Redis에서 키로 값 조회
     * @param key String 조회 키
     * @param type Class<T> 반환 타입 클래스
     * @return T 조회된 값
     */
    <T> T getByKey(String key, Class<T> type);

    /**
     * Redis에서 키 삭제
     * @param key String 삭제할 키
     */
    void delete(String key);

}
