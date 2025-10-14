package com.bigtablet.bigtablethompageserver.domain.recruit.application.service.impl;

import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request.RegisterRecruitRequest;
import com.bigtablet.bigtablethompageserver.domain.recruit.application.service.RecruitService;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa.RecruitJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitJpaRepository recruitJpaRepository;

    @Override
    public void saveRecruit(RegisterRecruitRequest request, Long jobId) {
        recruitJpaRepository.save(RecruitEntity.builder()
                .jobId(jobId)
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .address(request.address())
                .addressDetail(request.addressDetail())
                .portfolio(request.portfolio())
                .coverLetter(request.coverLetter())
                .educationLevel(request.educationLevel())
                .schoolName(request.schoolName())
                .admissionYear(request.admissionYear())
                .graduationYear(request.graduationYear())
                .department(request.department())
                .military(request.military())
                .attachment1(request.attachment1())
                .attachment2(request.attachment2())
                .attachment3(request.attachment3())
                .build());
    }

    @Override
    public Recruit getRecruit(Long idx) {
        return recruitJpaRepository
                .findById(idx)
                .map(Recruit::toRecruit)
                .orElseThrow(()-> RecruitNotFoundException.EXCEPTION);
    }

    @Override
    public List<Recruit> getAllRecruit() {
        return recruitJpaRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Recruit::toRecruit)
                .toList();
    }

    @Override
    public List<Recruit> getAllRecruitBYJobId(Long jobId) {
        return recruitJpaRepository
                .findAllByJobIdOrderByCreatedAtDesc(jobId)
                .stream()
                .map(Recruit::toRecruit)
                .toList();
    }

}
