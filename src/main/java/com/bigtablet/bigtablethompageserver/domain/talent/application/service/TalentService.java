package com.bigtablet.bigtablethompageserver.domain.talent.application.service;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa.TalentJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalentService {

    private final TalentJpaRepository talentJpaRepository;

    /**
     * 인재풀 등록
     * @param email String 이메일
     * @param name String 이름
     * @param department String 부서
     * @param portfolioUrl String 포트폴리오 URL
     * @param etcUrl List<String> 기타 URL 목록
     * @return void
     */
    @Transactional
    public void save(
            String email,
            String name,
            String department,
            String portfolioUrl,
            List<String> etcUrl
    ) {
        log.info("[TalentService] save - email={}, name={}", email, name);
        talentJpaRepository.save(TalentEntity.builder()
                .email(email)
                .name(name)
                .department(department)
                .portfolioUrl(portfolioUrl)
                .etcUrl(etcUrl)
                .isActive(true)
                .build());
    }

    /**
     * 인재 활성 상태 변경
     * @param idx Long 인재 ID
     * @param isActive boolean 활성 여부
     * @return void
     */
    @Transactional
    public void editActive(Long idx, boolean isActive) {
        log.info("[TalentService] editActive - idx={}, isActive={}", idx, isActive);
        TalentEntity entity = talentJpaRepository
                .findByIdx(idx)
                .orElseThrow(() -> TalentNotFoundException.EXCEPTION);
        if (isActive) {
            entity.activate();
        } else {
            entity.deactivate();
        }
    }

}
