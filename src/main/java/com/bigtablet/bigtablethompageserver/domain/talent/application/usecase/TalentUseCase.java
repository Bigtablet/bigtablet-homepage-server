package com.bigtablet.bigtablethompageserver.domain.talent.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.talent.application.query.TalentQueryService;
import com.bigtablet.bigtablethompageserver.domain.talent.application.response.TalentResponse;
import com.bigtablet.bigtablethompageserver.domain.talent.application.service.TalentService;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SearchTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SendEmailToTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentAlreadyExistException;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentIsEmptyException;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentNotFoundException;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TalentUseCase {

    private final TalentService talentService;
    private final TalentQueryService talentQueryService;
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

    public void sendMailToTalent(SendEmailToTalentRequest request) {
        Talent talent = talentService.findByIdx(request.idx());
        String content = mailTemplateRenderer.renderOfferEmail(talent.name(), request.text());
        emailService.sendRecruit(talent.email(), "[Bigtablet, Inc. 채용]", content);
    }

    public TalentResponse getTalent(Long idx) {
        Talent talent = talentService.findByIdx(idx);
        return TalentResponse.of(talent);
    }

    public List<TalentResponse> getTalentList(PageRequest request) {
        List<Talent> talents = talentQueryService.findAllTalents(request.getPage(), request.getSize());
        checkTalentsIsEmpty(talents);
        return talents.stream().map(TalentResponse::of).toList();
    }

    public List<TalentResponse> searchTalent(SearchTalentRequest request) {
        List<Talent> talents = talentQueryService.searchTalents(
                request.getKeyword(),
                request.getPage(),
                request.getSize()
        );
        checkTalentsIsEmpty(talents);
        return talents.stream().map(TalentResponse::of).toList();
    }

    private void checkTalentsIsEmpty(List<Talent> talents) {
        if (talents.isEmpty()) {
            throw TalentIsEmptyException.EXCEPTION;
        }
    }

}
