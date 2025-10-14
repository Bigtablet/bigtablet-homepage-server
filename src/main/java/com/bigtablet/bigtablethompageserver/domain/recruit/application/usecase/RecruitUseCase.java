package com.bigtablet.bigtablethompageserver.domain.recruit.application.usecase;

import com.bigtablet.bigtablethompageserver.domain.job.application.service.JobService;
import com.bigtablet.bigtablethompageserver.domain.job.client.dto.Job;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitUseCase {

    private final RecruitService recruitService;
    private final JobService jobService;

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

    public Job getJobById(Long jobId){
        return jobService.getJob(jobId);
    }

}
