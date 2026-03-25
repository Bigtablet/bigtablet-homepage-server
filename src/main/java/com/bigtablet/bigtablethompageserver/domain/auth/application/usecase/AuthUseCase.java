package com.bigtablet.bigtablethompageserver.domain.auth.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.service.AuthService;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.RefreshTokenRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignInRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignUpRequest;
import com.bigtablet.bigtablethompageserver.domain.user.application.query.UserQueryService;
import com.bigtablet.bigtablethompageserver.domain.user.application.service.UserService;
import com.bigtablet.bigtablethompageserver.domain.user.domain.model.User;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUseCase {

    private final AuthService authService;
    private final UserService userService;
    private final UserQueryService userQueryService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final MailTemplateRenderer mailTemplateRenderer;

    /**
     * 회원가입 (이메일 중복 검증 후 저장)
     * @param request SignUpRequest 회원가입 요청 정보
     * @return void
     */
    public void signUp(SignUpRequest request) {
        log.info("[AuthUseCase] signUp - email={}", request.email());
        userQueryService.checkEmailExists(request.email());
        userService.save(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );
    }

    /**
     * 로그인 (비밀번호 검증 후 JWT 발급)
     * @param request SignInRequest 로그인 요청 정보
     * @return JsonWebTokenResponse JWT 토큰 응답
     */
    public JsonWebTokenResponse signIn(SignInRequest request) {
        log.info("[AuthUseCase] signIn - email={}", request.email());
        User user = userQueryService.find(request.email());
        authService.checkPassword(request.password(), user.password());
        return authService.generateToken(request.email());
    }

    /**
     * 토큰 갱신 (리프레시 토큰으로 액세스 토큰 재발급)
     * @param request RefreshTokenRequest 토큰 갱신 요청 정보
     * @return RefreshTokenResponse 갱신된 토큰 응답
     */
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        log.info("[AuthUseCase] refresh");
        return authService.refreshToken(request.refreshToken());
    }

    /**
     * 이메일 인증 코드 발송
     * @param userEmail String 인증 코드를 발송할 이메일 주소
     * @return void
     */
    public void sendVerificationEmail(String userEmail) {
        log.info("[AuthUseCase] sendVerificationEmail - email={}", userEmail);
        String authCode = authService.createRandomNum(userEmail);
        String content = mailTemplateRenderer.renderAuthCodeEmail(authCode);
        emailService.sendNoReply(userEmail, "[Bigtablet, Inc.] 이메일 인증 코드", content);
    }

    /**
     * 이메일 인증 코드 검증
     * @param email String 인증할 이메일 주소
     * @param authCode String 인증 코드
     * @return void
     */
    public void checkAuthCode(String email, String authCode) {
        log.info("[AuthUseCase] checkAuthCode - email={}", email);
        authService.checkAuthNum(email, authCode);
    }

}
