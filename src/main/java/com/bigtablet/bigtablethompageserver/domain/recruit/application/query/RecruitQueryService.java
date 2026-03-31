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

    /**
     * ID로 지원서 조회
     * @param idx Long 지원서 ID
     * @return Recruit 지원서 도메인 객체
     */
    public Recruit find(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        return Recruit.of(entity);
    }

    /**
     * 전체 지원서 목록 조회 (최신순)
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findAll() {
        return recruitJpaRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Recruit::of)
                .toList();
    }

    /**
     * 채용 공고별 지원서 목록 조회 (최신순)
     * @param jobId Long 채용 공고 ID
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findAllByJobId(Long jobId) {
        return recruitJpaRepository
                .findAllByJobIdOrderByCreatedAtDesc(jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    /**
     * 상태별 지원서 목록 조회 (오래된순)
     * @param status Status 지원서 상태
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findAllByStatus(Status status) {
        return recruitJpaRepository
                .findAllByStatusOrderByCreatedAtAsc(status)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    /**
     * 상태 및 채용 공고별 지원서 목록 조회 (오래된순)
     * @param status Status 지원서 상태
     * @param jobId Long 채용 공고 ID
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findAllByStatusAndJobId(Status status, Long jobId) {
        return recruitJpaRepository
                .findAllByStatusAndJobIdOrderByCreatedAtAsc(status, jobId)
                .stream()
                .map(Recruit::of)
                .toList();
    }

    /**
     * 지원서 상태 검증 (서류 전형 상태인 경우 예외 발생)
     * @param idx Long 지원서 ID
     * @return void
     */
    public void checkStatus(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        if (entity.getStatus().equals(Status.DOCUMENT)) {
            throw RecruitStatusErrorException.EXCEPTION;
        }
    }

}
