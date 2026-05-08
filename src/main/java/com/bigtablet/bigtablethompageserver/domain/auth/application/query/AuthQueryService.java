package com.bigtablet.bigtablethompageserver.domain.auth.application.query;

import com.bigtablet.bigtablethompageserver.domain.user.exception.PasswordWrongException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.infra.email.exception.EmailCodeValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;

    /**
     * 비밀번호 일치 여부 검증 (불일치 시 예외 발생)
     * @param rawPassword String 평문 비밀번호
     * @param hashedPassword String 해시된 비밀번호
     * @return void
     */
    public void checkPassword(String rawPassword, String hashedPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw PasswordWrongException.EXCEPTION;
        }
    }

    /**
     * 이메일 인증 코드 검증 (불일치 시 예외 발생, 일치 시 일회성 코드를 Redis에서 제거)
     * @param email String 사용자 이메일
     * @param authCode String 인증 코드
     * @return void
     */
    public void checkAuthNum(String email, String authCode) {
        log.info("[AuthQueryService] checkAuthNum - email={}", email);
        String code = redisRepository.getByKey(email, String.class);
        if (!Objects.equals(code, authCode)) {
            throw EmailCodeValidException.EXCEPTION;
        }
        redisRepository.delete(email);
    }

}
