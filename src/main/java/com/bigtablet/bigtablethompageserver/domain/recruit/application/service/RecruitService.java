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

    List<Recruit> getAllRecruitByStatus(Status status);

    List<Recruit> getAllRecruitByStatusAndJobId(Status status, Long jobId);

    void editStatus(Status status, Long idx);

    void acceptRecruit(Long idx);

    void rejectRecruit(Long idx);

}
