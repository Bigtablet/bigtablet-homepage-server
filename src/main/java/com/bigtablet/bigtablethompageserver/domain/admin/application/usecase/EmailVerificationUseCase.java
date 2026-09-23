package com.bigtablet.bigtablethompageserver.domain.admin.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.admin.application.query.AdminQueryService;
import com.bigtablet.bigtablethompageserver.domain.admin.application.service.EmailVerificationService;
import com.bigtablet.bigtablethompageserver.global.common.util.RateLimiter;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 어드민 이메일 OTP 발급·검증 흐름을 조합한다.
 *
 * <p>로그는 {@code EmailVerificationService} 가 마스킹된 이메일로 남기므로 여기서 다시 남기지 않는다 —
 * 두 계층이 같은 요청을 각각 기록하면 인증 경로의 로그가 요청당 두 줄로 늘어난다.
 */
@Component
@RequiredArgsConstructor
public class EmailVerificationUseCase {

    // 어드민 이메일 인증 코드 메일 제목
    private static final String AUTH_CODE_MAIL_SUBJECT = "[Bigtablet, Inc.] 어드민 이메일 인증 코드";
    // IP 기준 발송 제한 — 1시간 5회
    private static final int SEND_IP_LIMIT = 5;
    // IP 기준 검증 제한 — 1시간 10회
    private static final int VERIFY_IP_LIMIT = 10;

    private final AdminQueryService adminQueryService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;
    private final MailTemplateRenderer mailTemplateRenderer;
    private final RateLimiter rateLimiter;

    /**
     * 어드민 이메일로 OTP 를 발급하고 메일로 발송한다.
     * <p>도메인 검증을 IP 레이트리밋보다 먼저 수행한다 — 허용되지 않은 요청이 카운터를 소모하면
     * 공격자가 정상 사용자의 IP 한도를 대신 태울 수 있다.
     * @param email 어드민 이메일
     * @param servletRequest 클라이언트 IP 추출용 요청
     */
    public void sendCode(String email, HttpServletRequest servletRequest) {
        adminQueryService.checkEmailDomain(email);
        rateLimiter.check("otp-send-ip:" + RateLimiter.clientIp(servletRequest), SEND_IP_LIMIT, Duration.ofHours(1));
        String authCode = emailVerificationService.issueCode(email);
        String content = mailTemplateRenderer.renderAuthCodeEmail(authCode);
        emailService.sendNoReply(email, AUTH_CODE_MAIL_SUBJECT, content);
    }

    /**
     * OTP 를 검증하고 인증 완료 플래그를 발행한다.
     * <p>발송 경로와 같은 이유로 도메인 검증을 IP 레이트리밋보다 먼저 수행한다.
     * @param email 어드민 이메일
     * @param authCode 클라이언트가 입력한 OTP
     * @param servletRequest 클라이언트 IP 추출용 요청
     */
    public void verifyCode(String email, String authCode, HttpServletRequest servletRequest) {
        adminQueryService.checkEmailDomain(email);
        rateLimiter.check("otp-verify-ip:" + RateLimiter.clientIp(servletRequest), VERIFY_IP_LIMIT, Duration.ofHours(1));
        emailVerificationService.verifyCode(email, authCode);
    }

}
