package com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.query.JobQueryService;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.query.RecruitQueryService;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.response.RecruitResponse;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.GetRecruitListRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
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

    /**
     * 채용 지원 등록 (지원 접수 확인 이메일 + 슬랙 알림 발송)
     * @param request RegisterRecruitRequest 채용 지원 등록 요청 정보
     * @return void
     */
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

    /**
     * 지원서 단건 조회
     * @param idx Long 지원서 식별자
     * @return RecruitResponse 지원서 응답
     */
    public RecruitResponse getRecruit(Long idx) {
        log.info("[RecruitUseCase] getRecruit - idx={}", idx);
        Recruit recruit = recruitQueryService.find(idx);
        return RecruitResponse.of(recruit);
    }

    /**
     * 페이지네이션 + jobId/status 필터로 지원서 목록 조회
     * @param request GetRecruitListRequest 지원서 목록 조회 요청 (page, size, jobId, status)
     * @return List<RecruitResponse> 지원서 응답 목록
     */
    public List<RecruitResponse> getRecruitList(GetRecruitListRequest request) {
        log.info("[RecruitUseCase] getRecruitList - page={}, size={}, jobId={}, status={}",
                request.getPage(), request.getSize(), request.getJobId(), request.getStatus());
        List<Recruit> recruits = recruitQueryService.findAllRecruits(
                request.getPage(),
                request.getSize(),
                request.getJobId(),
                request.getStatus()
        );
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    /**
     * 지원서 상태 변경 (면접 안내 이메일 발송)
     * @param status Status 변경할 지원서 상태
     * @param idx Long 지원서 식별자
     * @return void
     */
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

    /**
     * 지원자 최종 합격 처리 (합격 이메일 발송)
     * @param idx Long 지원서 식별자
     * @return void
     */
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

    /**
     * 지원자 최종 불합격 처리 (불합격 이메일 발송)
     * @param idx Long 지원서 식별자
     * @return void
     */
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

}
