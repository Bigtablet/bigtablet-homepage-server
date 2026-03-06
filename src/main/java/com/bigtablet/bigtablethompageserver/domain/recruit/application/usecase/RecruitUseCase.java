package com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.query.JobQueryService;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.query.RecruitQueryService;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.response.RecruitResponse;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import com.bigtablet.bigtablethompageserver.global.infra.slack.exception.SlackErrorException;
import com.bigtablet.bigtablethompageserver.global.infra.slack.service.SlackNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecruitUseCase {

    private final RecruitService recruitService;
    private final RecruitQueryService recruitQueryService;
    private final EmailService emailService;
    private final JobQueryService jobQueryService;
    private final MailTemplateRenderer mailTemplateRenderer;
    private final SlackNotifier slackNotifier;

    public void registerRecruit(RegisterRecruitRequest request) {
        log.info("[RecruitUseCase] registerRecruit - jobId={}, name={}", request.jobId(), request.name());
        Job job = jobQueryService.find(request.jobId());
        jobQueryService.checkIsExpired(job);
        Recruit recruit = recruitService.save(
                job.idx(),
                request.name(),
                request.phoneNumber(),
                request.email(),
                request.address(),
                request.addressDetail(),
                request.portfolio(),
                request.coverLetter(),
                request.profileImage(),
                request.educationLevel(),
                request.schoolName(),
                request.admissionYear(),
                request.graduationYear(),
                request.department(),
                request.military(),
                request.attachment1(),
                request.attachment2(),
                request.attachment3()
        );
        String content = mailTemplateRenderer.renderApplyConfirmEmail(request.name(), job.title(), LocalDateTime.now());
        emailService.sendRecruit(
                request.email(),
                "[Bigtablet, Inc. 채용] " + request.name() + "님, 지원 접수 완료 안내드립니다",
                content
        );
        try {
            slackNotifier.sendApplicantNotification(
                    job.title(),
                    recruit.name(),
                    recruit.idx()
            );
        } catch (Exception e) {
            throw SlackErrorException.EXCEPTION;
        }
    }

    public RecruitResponse getRecruit(Long idx) {
        log.info("[RecruitUseCase] getRecruit - idx={}", idx);
        Recruit recruit = recruitQueryService.find(idx);
        return RecruitResponse.of(recruit);
    }

    public List<RecruitResponse> getAllRecruit() {
        log.info("[RecruitUseCase] getAllRecruit");
        List<Recruit> recruits = recruitQueryService.findAll();
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByJobId(Long jobId) {
        log.info("[RecruitUseCase] getAllRecruitByJobId - jobId={}", jobId);
        Job job = jobQueryService.find(jobId);
        List<Recruit> recruits = recruitQueryService.findAllByJobId(job.idx());
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByStatus(Status status) {
        log.info("[RecruitUseCase] getAllRecruitByStatus - status={}", status);
        List<Recruit> recruits = recruitQueryService.findAllByStatus(status);
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByStatusAndJobId(Status status, Long jobId) {
        log.info("[RecruitUseCase] getAllRecruitByStatusAndJobId - status={}, jobId={}", status, jobId);
        Job job = jobQueryService.find(jobId);
        List<Recruit> recruits = recruitQueryService.findAllByStatusAndJobId(status, job.idx());
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public void editStatus(Status status, Long idx) {
        log.info("[RecruitUseCase] editStatus - idx={}, status={}", idx, status);
        Recruit recruit = recruitQueryService.find(idx);
        String content = mailTemplateRenderer.renderRecruitEmail(recruit.name(), status);
        recruitService.editStatus(status, idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 면접 전형 안내드립니다",
                content
        );
    }

    public void acceptRecruit(Long idx) {
        log.info("[RecruitUseCase] acceptRecruit - idx={}", idx);
        Recruit recruit = recruitQueryService.find(idx);
        String content = mailTemplateRenderer.renderAcceptEmail(recruit.name());
        recruitQueryService.checkStatus(recruit.idx());
        recruitService.accept(recruit.idx());
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    public void rejectRecruit(Long idx) {
        log.info("[RecruitUseCase] rejectRecruit - idx={}", idx);
        Recruit recruit = recruitQueryService.find(idx);
        String content = mailTemplateRenderer.renderRejectEmail(recruit.name());
        recruitService.reject(recruit.idx());
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    private void checkRecruitsIsEmpty(List<Recruit> recruits) {
        if (recruits.isEmpty()) {
            throw RecruitIsEmptyException.EXCEPTION;
        }
    }

}
