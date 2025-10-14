package com.bigtablet.bigtablethompageserver.domain.recruit.application.service;

import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;

import java.util.List;

public interface RecruitService {

    void saveRecruit(RegisterRecruitRequest request, Long jobId);

    Recruit getRecruit(Long idx);

    List<Recruit> getAllRecruit();

    List<Recruit> getAllRecruitBYJobId(Long jobId);

}
