package com.bigtablet.bigtablethompageserver.domain.talent.application.service;

import com.bigtablet.bigtablethompageserver.domain.talent.domain.entity.TalentEntity;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.model.Talent;
import com.bigtablet.bigtablethompageserver.domain.talent.domain.repository.jpa.TalentJpaRepository;
import com.bigtablet.bigtablethompageserver.domain.talent.exception.TalentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentService {

    private final TalentJpaRepository talentJpaRepository;

    public void saveTalent(
            String email,
            String name,
            String department,
            String portfolioUrl,
            List<String> etcUrl
    ) {
        talentJpaRepository.save(TalentEntity.builder()
                .email(email)
                .name(name)
                .department(department)
                .portfolioUrl(portfolioUrl)
                .etcUrl(etcUrl)
                .build());
    }

    public Talent findByIdx(Long idx) {
        return talentJpaRepository
                .findById(idx)
                .map(Talent::of)
                .orElseThrow(()->TalentNotFoundException.EXCEPTION);
    }

}
