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

    public void signUp(SignUpRequest request) {
        log.info("[AuthUseCase] signUp - email={}", request.email());
        userQueryService.checkEmailExists(request.email());
        userService.save(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );
    }

    public JsonWebTokenResponse signIn(SignInRequest request) {
        log.info("[AuthUseCase] signIn - email={}", request.email());
        User user = userQueryService.find(request.email());
        authService.checkPassword(request.password(), user.password());
        return authService.generateToken(request.email());
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        log.info("[AuthUseCase] refresh");
        return authService.refreshToken(request.refreshToken());
    }

    public void sendVerificationEmail(String userEmail) {
        log.info("[AuthUseCase] sendVerificationEmail - email={}", userEmail);
        String authCode = authService.createRandomNum(userEmail);
        String content = mailTemplateRenderer.renderAuthCodeEmail(authCode);
        emailService.sendNoReply(userEmail, "[Bigtablet, Inc.] 이메일 인증 코드", content);
    }

    public void checkAuthCode(String email, String authCode) {
        log.info("[AuthUseCase] checkAuthCode - email={}", email);
        authService.checkAuthNum(email, authCode);
    }

}
