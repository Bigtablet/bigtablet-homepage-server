package com.bigtablet.bigtablethompageserver.global.infra.email.renderer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailTemplateRenderer {

    private final TemplateEngine templateEngine;

    public String renderSignupCode(String companyId, String tempCode) {
        Context context = new Context();
        context.setVariable("companyId", companyId);
        context.setVariable("tempCode", tempCode);
        return templateEngine.process("signup-code", context);
    }

    public String renderEmailVerification(String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process("email-verification", context);
    }

}
