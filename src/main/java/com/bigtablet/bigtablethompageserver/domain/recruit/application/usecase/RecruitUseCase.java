package com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.Job;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.global.infra.email.renderer.MailTemplateRenderer;
import com.bigtablet.bigtablethompageserver.global.infra.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitUseCase {

    private final RecruitService recruitService;
    private final EmailService emailService;
    private final JobService jobService;
    private final MailTemplateRenderer mailTemplateRenderer;

    public void registerRecruit(RegisterRecruitRequest request){
        Job job = getJobById(request.jobId());
        jobService.checkJobIsExpired(job);
        recruitService.saveRecruit(request, job.idx());
        String content = mailTemplateRenderer.renderApplyConfirmEmail(request.name(), job.title(), LocalDateTime.now());
        emailService.sendRecruit(
                request.email(),
                "[Bigtablet, Inc. 채용] " + request.name() + "님, 지원 접수 완료 안내드립니다",
                content
        );
    }

    public Recruit getRecruit(Long idx){
        return recruitService.getRecruit(idx);
    }

    public List<Recruit> getAllRecruit() {
        return recruitService.getAllRecruit();
    }

    public List<Recruit> getAllRecruitByJobId(Long jobId){
        Job job = getJobById(jobId);
        return recruitService.getAllRecruitBYJobId(job.idx());
    }

    public List<Recruit> getAllRecruitByStatus(Status status){
        return recruitService.getAllRecruitByStatus(status);
    }

    public List<Recruit> getAllRecruitByStatusAndJobId(Status status, Long jobId){
        Job job = getJobById(jobId);
        return recruitService.getAllRecruitByStatusAndJobId(status, job.idx());
    }

    public void editStatus(Status status, Long idx){
        Recruit recruit = getRecruit(idx);
        String content = mailTemplateRenderer.renderRecruitEmail(recruit.name(),  status);
        recruitService.editStatus(status ,idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 면접 전형 안내드립니다",
                content
        );
    }

    public void acceptRecruit(Long idx){
        Recruit recruit = getRecruit(idx);
        String content = mailTemplateRenderer.renderAcceptEmail(recruit.name());
        recruitService.acceptRecruit(idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    public void rejectRecruit(Long idx){
        Recruit recruit = getRecruit(idx);
        String content = mailTemplateRenderer.renderRejectEmail(recruit.name());
        recruitService.rejectRecruit(idx);
        emailService.sendRecruit(
                recruit.email(),
                "[Bigtablet, Inc. 채용] " + recruit.name() + "님, 채용 전형 최종 결과 안내드립니다",
                content
        );
    }

    public Job getJobById(Long jobId){
        return jobService.getJob(jobId);
    }

}
