package com.bigtablet.bigtablethompageserver.domain.admin.application.query;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.EmailNotCertifiedException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailVerificationQueryService {

    // 인증 완료 플래그 Redis 키 접두어 (EmailVerificationService와 동일 prefix 유지)
    private static final String CERT_KEY_PREFIX = "admin-email-cert:";

    private final RedisRepository redisRepository;

    /**
     * 해당 이메일이 OTP 검증을 통과했는지 확인 (Redis 인증 완료 플래그 존재 여부)
     * @param email String 어드민 이메일
     * @return void (검증 실패 시 예외)
     */
    public void checkCertified(String email) {
        String flag = redisRepository.getByKey(CERT_KEY_PREFIX + email.toLowerCase(), String.class);
        if (flag == null) {
            throw EmailNotCertifiedException.EXCEPTION;
        }
    }

}
