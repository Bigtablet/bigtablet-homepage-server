package com.bigtablet.bigtablethompageserver.domain.recruit.application.query;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.entity.RecruitEntity;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.model.Recruit;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.jpa.RecruitJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.domain.repository.query.RecruitQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitNotFoundException;
import com.bigtablet.bigtablethompageserver.domain.recruit.exception.RecruitStatusErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitQueryService {

    private final RecruitJpaRepository recruitJpaRepository;
    private final RecruitQueryRepository recruitQueryRepository;

    /**
     * ID로 지원서 조회
     * @param idx 지원서 ID
     * @return Recruit 지원서 도메인 객체
     */
    public Recruit find(Long idx) {
        RecruitEntity entity = recruitJpaRepository
                .findById(idx)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
        return Recruit.of(entity);
    }

    /**
     * 페이지네이션 + jobId/status 필터로 지원서 목록 조회
     * @param page 페이지 번호 (1부터 시작)
     * @param size 페이지 크기
     * @param jobId 채용 공고 ID (필수)
     * @param status 지원서 상태 (nullable)
     * @return List<Recruit> 지원서 도메인 객체 목록
     */
    public List<Recruit> findAllRecruits(
            int page,
            int size,
            Long jobId,
            Status status
    ) {
        return recruitQueryRepository.findRecruits(page, size, jobId, status);
    }

    /**
     * 지원서 상태 검증 (서류 전형 상태인 경우 예외 발생)
     * @param recruit 검증할 지원서 도메인 객체
     */
    public void checkStatus(Recruit recruit) {
        if (recruit.status().equals(Status.DOCUMENT)) {
            throw RecruitStatusErrorException.EXCEPTION;
        }
    }

}
