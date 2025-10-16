package com.bigtablet.bigtablethompageserver.domain.auth.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.auth.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.response.RefreshTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.auth.application.service.AuthService;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.RefreshTokenRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignInRequest;
import com.bigtablet.bigtablethompageserver.domain.auth.client.request.SignUpRequest;
import com.bigtablet.bigtablethompageserver.domain.user.client.dto.User;
import com.bigtablet.bigtablethompageserver.domain.user.domain.entity.UserEntity;
import com.bigtablet.bigtablethompageserver.domain.user.application.service.UserService;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final AuthService authService;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final MailTemplateRenderer mailTemplateRenderer;

    private String authCode;

    public void signUp(SignUpRequest request) {
        userService.checkUserEmail(request.email());
        userService.save(UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .build()
        );
    }

    public JsonWebTokenResponse signIn(SignInRequest request) {
        User user = userService.getUser(request.email());
        authService.checkPassword(request.password(), user.password());
        return authService.generateToken(request.email());
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        return authService.refreshToken(request.refreshToken());
    }

    public void sendVerificationEmail(String userEmail) {
        createAuthCode(userEmail);
        String content = mailTemplateRenderer.renderAuthCodeEmail(authCode);
        emailService.sendNoReply(userEmail, "Bigtablet Inc Email Verification", content);
    }

    public void checkAuthCode(String email, String authCode) {
        authService.checkAuthNum(email, authCode);
    }

    private void createAuthCode(String email) {
        authCode = authService.createRandomNum(email);
    }

}
