package com.bigtablet.bigtablethompageserver.domain.admin.application.service;

import com.bigtablet.bigtablethompageserver.domain.admin.exception.EmailCodeMismatchException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import com.bigtablet.bigtablethompageserver.global.security.admin.config.AdminAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;
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

    private final RedisRepository redisRepository;
    private final EmailService emailService;
    private final MailTemplateRenderer mailTemplateRenderer;
    private final AdminAuthProperties adminAuthProperties;

    /**
     * 6자리 OTP 생성 후 Redis에 저장하고 이메일로 발송 (application.admin.otp-ttl 적용)
     * @param email String 어드민 이메일
     * @return void
     */
    public void sendCode(String email) {
        log.info("[EmailVerificationService] sendCode - email={}", email);
        String normalized = email.toLowerCase();
        // 기존 OTP가 남아있으면 덮어쓰기 위해 선제거
        Optional.ofNullable(redisRepository.getByKey(OTP_KEY_PREFIX + normalized, String.class))
                .ifPresent(value -> redisRepository.delete(OTP_KEY_PREFIX + normalized));
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
     * @param email String 어드민 이메일
     * @param authCode String 클라이언트가 입력한 OTP
     * @return void
     */
    public void verifyCode(String email, String authCode) {
        log.info("[EmailVerificationService] verifyCode - email={}", email);
        String normalized = email.toLowerCase();
        String savedCode = redisRepository.getByKey(OTP_KEY_PREFIX + normalized, String.class);
        if (savedCode == null || !Objects.equals(savedCode, authCode)) {
            throw EmailCodeMismatchException.EXCEPTION;
        }
        redisRepository.delete(OTP_KEY_PREFIX + normalized);
        redisRepository.save(CERT_KEY_PREFIX + normalized, "1", (int) adminAuthProperties.certTtl().toSeconds(), TimeUnit.SECONDS);
    }

}
