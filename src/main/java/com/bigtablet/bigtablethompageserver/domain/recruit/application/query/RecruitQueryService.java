package com.bigtablet.bigtablethompageserver.domain.recruit.application.query;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa.RecruitJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitNotFoundException;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitStatusErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryService {

    private final RecruitJpaRepository recruitJpaRepository;

    public Recruit find(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        return Recruit.of(entity);
    }

    public List<Recruit> findAll() {
        return recruitJpaRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByJobId(Long jobId) {
        return recruitJpaRepository
                .findAllByJobIdOrderByCreatedAtDesc(jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByStatus(Status status) {
        return recruitJpaRepository
                .findAllByStatusOrderByCreatedAtAsc(status)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public List<Recruit> findAllByStatusAndJobId(Status status, Long jobId) {
        return recruitJpaRepository
                .findAllByStatusAndJobIdOrderByCreatedAtAsc(status, jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    public void checkStatus(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        if (entity.getStatus().equals(Status.DOCUMENT)) {
            throw RecruitStatusErrorException.EXCEPTION;
        }
    }

}
