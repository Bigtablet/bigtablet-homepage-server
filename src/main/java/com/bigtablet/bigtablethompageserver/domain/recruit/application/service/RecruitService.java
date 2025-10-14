package com.bigtablet.bigtablethompageserver.domain.recruit.application.service;

import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import jakarta.transaction.Transactional;

import java.util.List;

public interface RecruitService {

    void saveRecruit(RegisterRecruitRequest request, Long jobId);

    Recruit getRecruit(Long idx);

    List<Recruit> getAllRecruit();

    List<Recruit> getAllRecruitBYJobId(Long jobId);

    @Transactional
    void editStatus(Status status, Long idx);

    @Transactional
    void acceptRecruit(Long idx);

    @Transactional
    void rejectRecruit(Long idx);

}
