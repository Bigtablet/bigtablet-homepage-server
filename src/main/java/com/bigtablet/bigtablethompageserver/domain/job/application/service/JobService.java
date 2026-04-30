package com.bigtablet.bigtablethompageserver.domain.job.application.service;

import com.bigtablet.bigtablethompageserver.domain.job.domain.entity.JobEntity;
import com.bigtablet.bigtablethompageserver.domain.job.domain.model.JobInput;
import com.bigtablet.bigtablethompageserver.domain.job.domain.repository.jpa.JobJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.job.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobJpaRepository jobJpaRepository;

    /**
     * 채용 공고 저장
     * @param input JobInput 채용 공고 입력 데이터
     * @return void
     */
    @Transactional
    public void save(JobInput input) {
        log.info("[JobService] save - title={}", input.title());
        jobJpaRepository.save(JobEntity.create(input));
    }

    /**
     * 채용 공고 수정
     * @param idx Long 채용 공고 ID
     * @param input JobInput 채용 공고 입력 데이터
     * @return void
     */
    @Transactional
    public void edit(Long idx, JobInput input) {
        log.info("[JobService] edit - idx={}", idx);
        JobEntity entity = jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION);
        entity.editJob(input);
    }

    /**
     * 채용 공고 비활성화
     * @param idx Long 채용 공고 ID
     * @return void
     */
    @Transactional
    public void deactivate(Long idx) {
        log.info("[JobService] deactivate - idx={}", idx);
        jobJpaRepository
                .findById(idx)
                .orElseThrow(() -> JobNotFoundException.EXCEPTION)
                .deactivate();
    }

    /**
     * 채용 공고 삭제
     * @param idx Long 채용 공고 ID
     * @return void
     */
    @Transactional
    public void delete(Long idx) {
        log.info("[JobService] delete - idx={}", idx);
        jobJpaRepository.deleteById(idx);
    }

}
