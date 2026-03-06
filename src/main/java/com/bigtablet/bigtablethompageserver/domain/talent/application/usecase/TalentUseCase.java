package com.bigtablet.bigtablethompageserver.domain.talent.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.talent.application.query.TalentQueryService;
import com.bigtablet.bigtablethompageserver.domain.talent.application.response.TalentResponse;
import com.bigtablet.bigtablethompageserver.domain.talent.application.service.TalentService;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.GetTalentListRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SearchTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SendEmailToTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TalentUseCase {

    private final TalentService talentService;
    private final TalentQueryService talentQueryService;
    private final EmailService emailService;
    private final MailTemplateRenderer mailTemplateRenderer;

    public void registerTalent(RegisterTalentRequest request) {
        log.info("[TalentUseCase] registerTalent - email={}, name={}", request.email(), request.name());
        talentQueryService.checkExistsByEmail(request.email());
        talentService.save(
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
        log.info("[TalentUseCase] sendMailToTalent - idx={}", request.idx());
        Talent talent = talentQueryService.find(request.idx());
        talentService.editActive(talent.idx(), false);
        String content = mailTemplateRenderer.renderOfferEmail(talent.name(), request.text());
        emailService.sendRecruit(talent.email(), "[Bigtablet, Inc. 채용]", content);
    }

    public TalentResponse getTalent(Long idx) {
        log.info("[TalentUseCase] getTalent - idx={}", idx);
        Talent talent = talentQueryService.find(idx);
        return TalentResponse.of(talent);
    }

    public List<TalentResponse> getTalentList(GetTalentListRequest request) {
        log.info("[TalentUseCase] getTalentList - isActive={}, page={}, size={}", request.isActive(), request.getPage(), request.getSize());
        List<Talent> talents = talentQueryService.findAllTalents(request.isActive(), request.getPage(), request.getSize());
        checkTalentsIsEmpty(talents);
        return talents.stream().map(TalentResponse::of).toList();
    }

    public List<TalentResponse> searchTalent(SearchTalentRequest request) {
        log.info("[TalentUseCase] searchTalent - keyword={}", request.getKeyword());
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
