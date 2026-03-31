package com.bigtablet.bigtablethompageserver.domain.job.application.query;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.Job;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.query.JobQueryRepository;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobIsExpiredException;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobQueryService {

    private final JobJpaRepository jobJpaRepository;
    private final JobQueryRepository jobQueryRepository;

    /**
     * ID로 채용 공고 조회
     * @param idx Long 채용 공고 ID
     * @return Job 채용 공고 도메인 객체
     */
    public Job find(Long idx) {
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        return Job.of(entity);
    }

    /**
     * 채용 공고 만료 여부 검증 (비활성 시 예외 발생)
     * @param job Job 검증할 채용 공고 도메인 객체
     * @return void
     */
    public void checkIsExpired(Job job) {
        if (!job.isActive()) {
            throw JobIsExpiredException.EXCEPTION;
        }
    }

    /**
     * 활성 채용 공고 목록 조건 조회
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @param title String 검색할 채용 공고 제목
     * @param department Department 부서 필터
     * @param education Education 학력 필터
     * @param recruitType RecruitType 채용 유형 필터
     * @return List<Job> 채용 공고 도메인 객체 목록
     */
    public List<Job> findJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findJobList(
                page,
                size,
                title,
                department,
                education,
                recruitType
        );
    }

    /**
     * 비활성 채용 공고 목록 조건 조회
     * @param page int 페이지 번호
     * @param size int 페이지 크기
     * @param title String 검색할 채용 공고 제목
     * @param department Department 부서 필터
     * @param education Education 학력 필터
     * @param recruitType RecruitType 채용 유형 필터
     * @return List<Job> 채용 공고 도메인 객체 목록
     */
    public List<Job> findDeactivateJobList(
            int page,
            int size,
            String title,
            Department department,
            Education education,
            RecruitType recruitType
    ) {
        return jobQueryRepository.findDeactivateJobList(
                page,
                size,
                title,
                department,
                education,
                recruitType
        );
    }

}
