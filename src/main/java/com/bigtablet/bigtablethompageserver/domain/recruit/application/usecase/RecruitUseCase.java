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
        recruitService.saveRecruit(request, job.idx());
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
        recruitService.editStatus(status ,idx);
    }

    public void acceptRecruit(Long idx){
        Recruit recruit = getRecruit(idx);
        String content = mailTemplateRenderer.renderAcceptEmail(recruit.name());
        recruitService.acceptRecruit(idx);
        emailService.sendRecruit(recruit.email(),"Bigtablet Inc - 최종 결과 안내", content);
    }

    public void rejectRecruit(Long idx){
        Recruit recruit = getRecruit(idx);
        String content = mailTemplateRenderer.renderRejectEmail(recruit.name());
        recruitService.rejectRecruit(idx);
        emailService.sendRecruit(recruit.email(),"Bigtablet Inc - 최종 결과 안내", content);
    }

    public Job getJobById(Long jobId){
        return jobService.getJob(jobId);
    }

}
