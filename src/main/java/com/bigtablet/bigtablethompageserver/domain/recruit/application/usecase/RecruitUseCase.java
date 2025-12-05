package com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.response.RecruitResponse;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecruitUseCase {

    private final RecruitService recruitService;
    private final EmailService emailService;
    private final JobService jobService;
    private final MailTemplateRenderer mailTemplateRenderer;

    public void registerRecruit(RegisterRecruitRequest request) {
        Job job = jobService.findById(request.jobId());
        jobService.checkJobIsExpired(job);
        recruitService.saveRecruit(
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
    }

    public RecruitResponse getRecruit(Long idx) {
        Recruit recruit = recruitService.findById(idx);
        return RecruitResponse.of(recruit);
    }

    public List<RecruitResponse> getAllRecruit() {
        List<Recruit> recruits = recruitService.findAll();
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByJobId(Long jobId) {
        Job job = jobService.findById(jobId);
        List<Recruit> recruits = recruitService.findAllByJobId(job.idx());
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByStatus(Status status) {
        List<Recruit> recruits = recruitService.findAllByStatus(status);
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public List<RecruitResponse> getAllRecruitByStatusAndJobId(Status status, Long jobId) {
        Job job = jobService.findById(jobId);
        List<Recruit> recruits = recruitService.findAllByStatusAndJobId(status, job.idx());
        checkRecruitsIsEmpty(recruits);
        return recruits.stream()
                .map(RecruitResponse::of)
                .toList();
    }

    public void editStatus(Status status, Long idx) {
        Recruit recruit = recruitService.findById(idx);
        String content = mailTemplateRenderer.renderRecruitEmail(recruit.name(), status);
        recruitService.updateStatus(status, idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 면접 전형 안내드립니다",
                content
        );
    }

    public void acceptRecruit(Long idx) {
        Recruit recruit = recruitService.findById(idx);
        String content = mailTemplateRenderer.renderAcceptEmail(recruit.name());
        recruitService.acceptRecruit(idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    public void rejectRecruit(Long idx) {
        Recruit recruit = recruitService.findById(idx);
        String content = mailTemplateRenderer.renderRejectEmail(recruit.name());
        recruitService.rejectRecruit(idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    public void checkRecruitsIsEmpty(List<Recruit> recruits) {
        if (recruits.isEmpty()) {
            throw RecruitIsEmptyException.EXCEPTION;
        }
    }

}
