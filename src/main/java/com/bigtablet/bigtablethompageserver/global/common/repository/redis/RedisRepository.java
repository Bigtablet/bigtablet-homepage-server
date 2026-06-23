package com.bigtablet.bigtablethompageserver.global.common.repository.redis;

import java.time.Duration;
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

    /**
     * 키 값을 원자적으로 1 증가시킨다. 최초 증가(값이 1) 시 TTL을 설정한다. (고정 윈도우 카운터)
     * @param key 카운터 키
     * @param ttl 최초 생성 시 적용할 만료 시간
     * @return 증가 후 카운트
     */
    long increment(String key, Duration ttl);

}
