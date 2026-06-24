package com.bigtablet.bigtablethompageserver.domain.admin.application.service;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.EmailCodeMismatchException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.common.util.RateLimiter;
import com.bigtablet.bigtablethompageserver.global.exception.TooManyRequestsException;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import com.bigtablet.bigtablethompageserver.global.security.admin.config.AdminAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    // OTP Redis 키 접두어
    private static final String OTP_KEY_PREFIX = "admin-email-otp:";
    // 인증 완료 플래그 Redis 키 접두어
    private static final String CERT_KEY_PREFIX = "admin-email-cert:";
    // 인증 시도 횟수 카운터 Redis 키 접두어
    private static final String ATTEMPT_KEY_PREFIX = "admin-email-attempt:";
    // OTP 검증 최대 시도 횟수 (초과 시 OTP 폐기 + 잠금)
    private static final int MAX_VERIFY_ATTEMPTS = 5;

    private final RedisRepository redisRepository;
    private final EmailService emailService;
    private final MailTemplateRenderer mailTemplateRenderer;
    private final AdminAuthProperties adminAuthProperties;
    private final RateLimiter rateLimiter;

    /**
     * 6자리 OTP 생성 후 Redis에 저장하고 이메일로 발송 (application.admin.otp-ttl 적용)
     * @param email 어드민 이메일
     */
    public void sendCode(String email) {
        log.info("[EmailVerificationService] sendCode - email={}", mask(email));
        String normalized = email.toLowerCase();
        // 발송 남용 방지: 동일 이메일 30초 1회 + 1시간 10회 제한
        rateLimiter.check("admin-email-send-cd:" + normalized, 1, Duration.ofSeconds(30));
        rateLimiter.check("admin-email-send:" + normalized, 10, Duration.ofHours(1));
        // 기존 OTP가 남아있으면 덮어쓰기 위해 선제거
        Optional.ofNullable(redisRepository.getByKey(OTP_KEY_PREFIX + normalized, String.class))
                .ifPresent(value -> redisRepository.delete(OTP_KEY_PREFIX + normalized));
        // 새 코드 발급 시 검증 시도 횟수 초기화
        redisRepository.delete(ATTEMPT_KEY_PREFIX + normalized);
        SecureRandom r = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(r.nextInt(10));
        }
        String authCode = code.toString();
        redisRepository.save(OTP_KEY_PREFIX + normalized, authCode, (int) adminAuthProperties.otpTtl().toSeconds(), TimeUnit.SECONDS);
        String content = mailTemplateRenderer.renderAuthCodeEmail(authCode);
        emailService.sendNoReply(email, "[Bigtablet, Inc.] 어드민 이메일 인증 코드", content);
    }

    /**
     * OTP 검증 후 인증 완료 플래그를 저장 (application.admin.cert-ttl 적용, 검증 성공 시 OTP는 즉시 삭제)
     * @param email 어드민 이메일
     * @param authCode 클라이언트가 입력한 OTP
     */
    public void verifyCode(String email, String authCode) {
        log.info("[EmailVerificationService] verifyCode - email={}", mask(email));
        String normalized = email.toLowerCase();
        // 무차별 대입 방어: 시도 횟수를 OTP 수명 동안 누적, 초과 시 OTP 폐기 + 잠금
        long attempts = redisRepository.increment(ATTEMPT_KEY_PREFIX + normalized, adminAuthProperties.otpTtl());
        if (attempts > MAX_VERIFY_ATTEMPTS) {
            redisRepository.delete(OTP_KEY_PREFIX + normalized);
            throw TooManyRequestsException.EXCEPTION;
        }
        String savedCode = redisRepository.getByKey(OTP_KEY_PREFIX + normalized, String.class);
        // 상수 시간 비교로 타이밍 사이드채널 차단
        if (savedCode == null || authCode == null
                || !MessageDigest.isEqual(savedCode.getBytes(StandardCharsets.UTF_8), authCode.getBytes(StandardCharsets.UTF_8))) {
            throw EmailCodeMismatchException.EXCEPTION;
        }
        redisRepository.delete(OTP_KEY_PREFIX + normalized);
        redisRepository.delete(ATTEMPT_KEY_PREFIX + normalized);
        redisRepository.save(CERT_KEY_PREFIX + normalized, "1", (int) adminAuthProperties.certTtl().toSeconds(), TimeUnit.SECONDS);
    }

    // 이메일 로컬파트를 마스킹하여 로그 PII 노출을 줄인다 (예: a***@bigtablet.com)
    private String mask(String email) {
        if (email == null) {
            return "null";
        }
        int at = email.indexOf('@');
        if (at <= 0) {
            return "***";
        }
        return email.charAt(0) + "***" + email.substring(at);
    }

}
