package com.bigtablet.bigtablethompageserver.domain.admin.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.admin.application.query.AdminQueryService;
import com.bigtablet.bigtablethompageserver.domain.admin.application.query.EmailVerificationQueryService;
import com.bigtablet.bigtablethompageserver.domain.admin.application.query.WebAuthnQueryService;
import com.bigtablet.bigtablethompageserver.domain.admin.application.response.JsonWebTokenResponse;
import com.bigtablet.bigtablethompageserver.domain.admin.application.response.WebAuthnOptionsResponse;
import com.bigtablet.bigtablethompageserver.domain.admin.application.service.AdminService;
import com.bigtablet.bigtablethompageserver.domain.admin.application.service.WebAuthnService;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnLoginFinishRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnLoginStartRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnRegisterFinishRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.client.dto.request.WebAuthnRegisterStartRequest;
import com.bigtablet.bigtablethompageserver.domain.admin.domain.model.Admin;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.CredentialAlreadyRegisteredException;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.WebAuthnAuthenticationFailedException;
import com.bigtablet.bigtablethompageserver.domain.admin.exception.WebAuthnRegistrationFailedException;
import com.bigtablet.bigtablethompageserver.global.common.repository.redis.RedisRepository;
import com.bigtablet.bigtablethompageserver.global.security.admin.config.AdminAuthProperties;
import com.bigtablet.bigtablethompageserver.global.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.exception.AssertionFailedException;
import com.yubico.webauthn.exception.RegistrationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebAuthnUseCase {

    private static final String WEBAUTHN_REG_KEY_PREFIX = "webauthn-reg:";
    private static final String WEBAUTHN_LOGIN_KEY_PREFIX = "webauthn-login:";

    // Yubico WebAuthn 라이브러리가 Jackson 2.x 기반이라 동일 버전을 직접 인스턴스화한다.
    // Spring Boot 4의 자동 설정 ObjectMapper는 Jackson 3 (tools.jackson) 빈이라 호환 안 됨.
    // findAndRegisterModules()로 classpath에 있는 모듈(parameter-names, jsr310 등)을 자동 등록하여
    // record 역직렬화와 Java 8 시간 타입 처리를 안전하게 지원한다.
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private final RelyingParty relyingParty;
    private final AdminQueryService adminQueryService;
    private final AdminService adminService;
    private final WebAuthnService webAuthnService;
    private final WebAuthnQueryService webAuthnQueryService;
    private final EmailVerificationQueryService emailVerificationQueryService;
    private final JwtProvider jwtProvider;
    private final RedisRepository redisRepository;
    private final AdminAuthProperties adminAuthProperties;

    /**
     * 물리키 등록 시작 — 챌린지 발급 + Redis 캐싱 (5분)
     * @param request 등록 시작 요청
     * @return WebAuthnOptionsResponse 등록 옵션 JSON
     */
    public WebAuthnOptionsResponse registerStart(WebAuthnRegisterStartRequest request) {
        log.info("[WebAuthnUseCase] registerStart - email={}", request.email());
        adminQueryService.checkEmailDomain(request.email());
        emailVerificationQueryService.checkCertified(request.email());
        String adminId = findOrCreateAdmin(request.email());
        UserIdentity userIdentity = UserIdentity.builder()
                .name(request.email())
                .displayName(request.email())
                .id(new ByteArray(adminId.getBytes(StandardCharsets.UTF_8)))
                .build();
        StartRegistrationOptions options = StartRegistrationOptions.builder()
                .user(userIdentity)
                .build();
        PublicKeyCredentialCreationOptions creationOptions = relyingParty.startRegistration(options);
        try {
            String optionsJson = creationOptions.toCredentialsCreateJson();
            String redisKey = WEBAUTHN_REG_KEY_PREFIX + request.email().toLowerCase();
            WebAuthnRegisterChallenge challenge = new WebAuthnRegisterChallenge(
                    creationOptions.toJson(),
                    adminId,
                    request.keyName()
            );
            redisRepository.save(
                    redisKey,
                    objectMapper.writeValueAsString(challenge),
                    (int) adminAuthProperties.challengeTtl().toSeconds(),
                    TimeUnit.SECONDS
            );
            return WebAuthnOptionsResponse.builder()
                    .options(optionsJson)
                    .build();
        } catch (JsonProcessingException e) {
            log.error("[WebAuthnUseCase] registerStart 직렬화 실패 - email={}", request.email(), e);
            throw WebAuthnRegistrationFailedException.EXCEPTION;
        }
    }

    /**
     * 물리키 등록 완료 — 크레덴셜 검증 후 저장
     * @param request 등록 완료 요청
     */
    @Transactional
    public void registerFinish(WebAuthnRegisterFinishRequest request) {
        log.info("[WebAuthnUseCase] registerFinish - email={}", request.email());
        String redisKey = WEBAUTHN_REG_KEY_PREFIX + request.email().toLowerCase();
        String challengeJson = redisRepository.getByKey(redisKey, String.class);
        if (challengeJson == null) {
            throw WebAuthnRegistrationFailedException.EXCEPTION;
        }
        WebAuthnRegisterChallenge challenge;
        try {
            challenge = objectMapper.readValue(challengeJson, WebAuthnRegisterChallenge.class);
        } catch (JsonProcessingException e) {
            log.error("[WebAuthnUseCase] 챌린지 역직렬화 실패 - email={}", request.email(), e);
            throw WebAuthnRegistrationFailedException.EXCEPTION;
        }
        try {
            PublicKeyCredentialCreationOptions creationOptions = PublicKeyCredentialCreationOptions.fromJson(challenge.optionsJson());
            PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential =
                    PublicKeyCredential.parseRegistrationResponseJson(request.credential());
            FinishRegistrationOptions finishOptions = FinishRegistrationOptions.builder()
                    .request(creationOptions)
                    .response(credential)
                    .build();
            RegistrationResult result = relyingParty.finishRegistration(finishOptions);
            String credentialId = result.getKeyId().getId().getBase64Url();
            String publicKeyCose = Base64.getEncoder().encodeToString(result.getPublicKeyCose().getBytes());
            webAuthnService.save(
                    challenge.adminId(),
                    credentialId,
                    publicKeyCose,
                    0L,
                    challenge.keyName()
            );
            redisRepository.delete(redisKey);
            log.info("[WebAuthnUseCase] registerFinish 성공 - adminId={}, credentialId={}", challenge.adminId(), credentialId);
        } catch (RegistrationFailedException | IOException e) {
            log.error("[WebAuthnUseCase] registerFinish 실패 - email={}", request.email(), e);
            throw WebAuthnRegistrationFailedException.EXCEPTION;
        }
    }

    /**
     * 물리키 로그인 시작 — 챌린지 발급 + Redis 캐싱 (5분)
     * @param request 로그인 시작 요청
     * @return WebAuthnOptionsResponse 로그인 옵션 JSON
     */
    public WebAuthnOptionsResponse loginStart(WebAuthnLoginStartRequest request) {
        log.info("[WebAuthnUseCase] loginStart - email={}", request.email());
        adminQueryService.checkEmailDomain(request.email());
        Admin admin = adminQueryService.findByEmail(request.email());
        if (!webAuthnQueryService.hasCredential(admin.id())) {
            throw WebAuthnAuthenticationFailedException.EXCEPTION;
        }
        StartAssertionOptions options = StartAssertionOptions.builder()
                .username(admin.id())
                .build();
        AssertionRequest assertionRequest = relyingParty.startAssertion(options);
        try {
            String optionsJson = assertionRequest.toCredentialsGetJson();
            String redisKey = WEBAUTHN_LOGIN_KEY_PREFIX + admin.id();
            redisRepository.save(
                    redisKey,
                    assertionRequest.toJson(),
                    (int) adminAuthProperties.challengeTtl().toSeconds(),
                    TimeUnit.SECONDS
            );
            return WebAuthnOptionsResponse.builder()
                    .options(optionsJson)
                    .build();
        } catch (JsonProcessingException e) {
            log.error("[WebAuthnUseCase] loginStart 직렬화 실패 - email={}", request.email(), e);
            throw WebAuthnAuthenticationFailedException.EXCEPTION;
        }
    }

    /**
     * 물리키 로그인 완료 — 어설션 검증 후 JWT 발급
     * @param request 로그인 완료 요청
     * @return JsonWebTokenResponse JWT (access + refresh)
     */
    @Transactional
    public JsonWebTokenResponse loginFinish(WebAuthnLoginFinishRequest request) {
        log.info("[WebAuthnUseCase] loginFinish - email={}", request.email());
        adminQueryService.checkEmailDomain(request.email());
        Admin admin = adminQueryService.findByEmail(request.email());
        String redisKey = WEBAUTHN_LOGIN_KEY_PREFIX + admin.id();
        String assertionRequestJson = redisRepository.getByKey(redisKey, String.class);
        if (assertionRequestJson == null) {
            throw WebAuthnAuthenticationFailedException.EXCEPTION;
        }
        try {
            AssertionRequest assertionRequest = AssertionRequest.fromJson(assertionRequestJson);
            PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> credential =
                    PublicKeyCredential.parseAssertionResponseJson(request.credential());
            FinishAssertionOptions finishOptions = FinishAssertionOptions.builder()
                    .request(assertionRequest)
                    .response(credential)
                    .build();
            AssertionResult result = relyingParty.finishAssertion(finishOptions);
            if (!result.isSuccess()) {
                throw WebAuthnAuthenticationFailedException.EXCEPTION;
            }
            String credentialId = result.getCredential().getCredentialId().getBase64Url();
            webAuthnService.editSignatureCount(credentialId, result.getSignatureCount());
            redisRepository.delete(redisKey);
            log.info("[WebAuthnUseCase] loginFinish 성공 - adminId={}", admin.id());
            return JsonWebTokenResponse.builder()
                    .accessToken(jwtProvider.generateAccessToken(admin.id(), admin.role().name()))
                    .refreshToken(jwtProvider.generateRefreshToken(admin.id()))
                    .build();
        } catch (AssertionFailedException | IOException e) {
            log.error("[WebAuthnUseCase] loginFinish 실패 - email={}", request.email(), e);
            throw WebAuthnAuthenticationFailedException.EXCEPTION;
        }
    }

    // 어드민 조회 또는 신규 생성 (등록용, 동시성 안전)
    // 이미 크레덴셜이 등록된 어드민에 대해서는 등록 차단 — 계정 탈취 방어
    private String findOrCreateAdmin(String email) {
        Admin existing = adminQueryService.findByEmailOrNull(email);
        if (existing != null) {
            if (webAuthnQueryService.hasCredential(existing.id())) {
                log.warn("[WebAuthnUseCase] 기존 크레덴셜 보유 어드민에 대한 등록 시도 차단 - email={}", email);
                throw CredentialAlreadyRegisteredException.EXCEPTION;
            }
            return existing.id();
        }
        try {
            return adminService.save(email);
        } catch (DataIntegrityViolationException ex) {
            log.warn("[WebAuthnUseCase] 동시 요청으로 어드민 중복 생성 시도 - email={}", email);
            Admin retried = adminQueryService.findByEmailOrNull(email);
            if (retried == null) {
                throw ex;
            }
            if (webAuthnQueryService.hasCredential(retried.id())) {
                throw CredentialAlreadyRegisteredException.EXCEPTION;
            }
            return retried.id();
        }
    }

    // Redis 저장용 챌린지 record
    record WebAuthnRegisterChallenge(
            String optionsJson,
            String adminId,
            String keyName
    ) {}

}
