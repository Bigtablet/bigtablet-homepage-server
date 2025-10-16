package com.bigtablet.bigtablethompageserver.global.infra.email.renderer;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailTemplateRenderer {

    private final TemplateEngine templateEngine;

    public String renderAuthCodeEmail(String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process("auth-code", context);
    }

    public String renderRecruitEmail(String name, Status status) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("status", status);
        return templateEngine.process("recruit-email", context);
    }

    public String renderAcceptEmail(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("accept-email", context);
    }

    public String renderRejectEmail(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("reject-email", context);
    }

}
