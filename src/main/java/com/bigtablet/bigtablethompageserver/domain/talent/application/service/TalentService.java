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
     * @param email 이메일
     * @param name 이름
     * @param department 부서
     * @param portfolioUrl 포트폴리오 URL
     * @param etcUrl 기타 URL 목록
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
     * @param idx 인재 ID
     * @param isActive 활성 여부
     */
    @Transactional
    public void editActive(Long idx, boolean isActive) {
        log.info("[TalentService] editActive - idx={}, isActive={}", idx, isActive);
        TalentEntity entity = talentJpaRepository
                .findById(idx)
                .orElseThrow(() -> TalentNotFoundException.EXCEPTION);
        entity.editActive(isActive);
    }

}
