package com.bigtablet.bigtablethompageserver.global.common.util;

import java.util.Collection;

public final class CollectionValidator {

    private CollectionValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 컬렉션이 비어있으면 지정된 예외를 던진다.
     * @param collection 검사할 컬렉션
     * @param exception 비어있을 때 던질 예외 (싱글턴 인스턴스 권장)
     * @param <T> 컬렉션 요소 타입
     */
    public static <T> void throwIfEmpty(Collection<T> collection, RuntimeException exception) {
        if (collection.isEmpty()) {
            throw exception;
        }
    }

}
