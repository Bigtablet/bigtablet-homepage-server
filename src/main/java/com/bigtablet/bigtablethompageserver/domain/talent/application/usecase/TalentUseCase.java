package com.bigtablet.bigtablethompageserver.domain.talent.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.talent.application.response.TalentResponse;
import com.bigtablet.bigtablethompageserver.domain.talent.application.service.TalentService;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentAlreadyExistException;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TalentUseCase {

    private final TalentService talentService;
    private final EmailService emailService;
    private final MailTemplateRenderer mailTemplateRenderer;

    public void registerTalent(RegisterTalentRequest request) {
        Talent talent = talentService.findByEmail(request.email());
        if (talent != null) {
            throw TalentAlreadyExistException.EXCEPTION;
        }
        talentService.saveTalent(
                request.email(),
                request.name(),
                request.department(),
                request.portfolioUrl(),
                request.etcUrl()
        );
        String content = mailTemplateRenderer.renderTalentEmail(request.name(), LocalDateTime.now());
        emailService.sendRecruit(request.email(), "[Bigtablet, Inc. 채용]", content);
    }

    public TalentResponse getTalent(Long idx) {
        Talent talent = talentService.findByIdx(idx);
        return TalentResponse.of(talent);
    }

}
