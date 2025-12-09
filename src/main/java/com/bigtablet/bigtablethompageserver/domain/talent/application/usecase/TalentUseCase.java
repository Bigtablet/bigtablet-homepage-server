package com.bigtablet.bigtablethompageserver.domain.talent.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.talent.application.query.TalentQueryService;
import com.bigtablet.bigtablethompageserver.domain.talent.application.response.TalentResponse;
import com.bigtablet.bigtablethompageserver.domain.talent.application.service.TalentService;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.GetTalentListRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.RegisterTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SearchTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request.SendEmailToTalentRequest;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentAlreadyExistException;
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
        long start = System.nanoTime();

        // 1. 이메일 중복 체크
        long t1Start = System.nanoTime();
        talentService.checkExistByEmail(request.email());
        long t1End = System.nanoTime();
        log.info("[registerTalent] checkExistByEmail took {} sec",
                (t1End - t1Start) / 1_000_000_000.0);

        // 2. 인재 저장
        long t2Start = System.nanoTime();
        talentService.saveTalent(
                request.email(),
                request.name(),
                request.department(),
                request.portfolioUrl(),
                request.etcUrl()
        );
        long t2End = System.nanoTime();
        log.info("[registerTalent] saveTalent took {} sec",
                (t2End - t2Start) / 1_000_000_000.0);

        // 3. 메일 템플릿 렌더링
        long t3Start = System.nanoTime();
        String content = mailTemplateRenderer.renderTalentEmail(request.name(), LocalDateTime.now());
        long t3End = System.nanoTime();
        log.info("[registerTalent] renderTalentEmail took {} sec",
                (t3End - t3Start) / 1_000_000_000.0);

        // 4. 메일 발송
        long t4Start = System.nanoTime();
        emailService.sendRecruit(request.email(), "[Bigtablet, Inc. 채용]", content);
        long t4End = System.nanoTime();
        log.info("[registerTalent] sendRecruit took {} sec",
                (t4End - t4Start) / 1_000_000_000.0);

        long end = System.nanoTime();
        log.info("[registerTalent] total execution took {} sec",
                (end - start) / 1_000_000_000.0);
    }

    public void sendMailToTalent(SendEmailToTalentRequest request) {
        Talent talent = talentService.findByIdx(request.idx());
        talentService.updateTalentIsActive(talent.idx(), false);
        String content = mailTemplateRenderer.renderOfferEmail(talent.name(), request.text());
        emailService.sendRecruit(talent.email(), "[Bigtablet, Inc. 채용]", content);
    }

    public TalentResponse getTalent(Long idx) {
        Talent talent = talentService.findByIdx(idx);
        return TalentResponse.of(talent);
    }

    public List<TalentResponse> getTalentList(GetTalentListRequest request) {
        List<Talent> talents = talentQueryService.findAllTalents(request.isActive(), request.getPage(), request.getSize());
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
