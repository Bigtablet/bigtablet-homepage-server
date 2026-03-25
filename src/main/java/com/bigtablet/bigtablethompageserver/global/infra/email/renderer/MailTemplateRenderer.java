package com.bigtablet.bigtablethompageserver.global.infra.email.renderer;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MailTemplateRenderer {

    private final TemplateEngine templateEngine;

    /**
     * 이메일 인증 코드 메일 템플릿 렌더링
     * @param authCode String 인증 코드
     * @return String HTML 내용
     */
    public String renderAuthCodeEmail(String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process("auth-code", context);
    }

    /**
     * 면접 전형 안내 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @param status Status 전형 상태
     * @return String HTML 내용
     */
    public String renderRecruitEmail(String name, Status status) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("status", status);
        return templateEngine.process("recruit-email", context);
    }

    /**
     * 최종 합격 안내 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @return String HTML 내용
     */
    public String renderAcceptEmail(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("accept-email", context);
    }

    /**
     * 최종 불합격 안내 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @return String HTML 내용
     */
    public String renderRejectEmail(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("reject-email", context);
    }

    /**
     * 지원 접수 확인 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @param position String 지원 포지션
     * @param applicationDate LocalDateTime 지원 일시
     * @return String HTML 내용
     */
    public String renderApplyConfirmEmail(String name, String position, LocalDateTime applicationDate) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("position", position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm 'KST'");
        String formattedDate = applicationDate.format(formatter);
        context.setVariable("applicationDate", formattedDate);
        return templateEngine.process("apply-confirm", context);
    }

    /**
     * 인재풀 등록 확인 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @param createdAt LocalDateTime 등록 일시
     * @return String HTML 내용
     */
    public String renderTalentEmail(String name, LocalDateTime createdAt) {
        Context context = new Context();
        context.setVariable("name", name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm 'KST'");
        String formattedDate = createdAt.format(formatter);
        context.setVariable("applicationDate", formattedDate);
        return templateEngine.process("talent-email", context);
    }

    /**
     * 면접 제안 메일 템플릿 렌더링
     * @param name String 지원자 이름
     * @param text String 면접 제안 내용
     * @return String HTML 내용
     */
    public String renderOfferEmail(String name, String text) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("content", text);
        return templateEngine.process("offer-email", context);
    }


}
